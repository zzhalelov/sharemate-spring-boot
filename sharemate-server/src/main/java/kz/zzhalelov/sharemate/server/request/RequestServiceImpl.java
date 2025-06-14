package kz.zzhalelov.sharemate.server.request;

import kz.zzhalelov.sharemate.server.exception.BadRequestException;
import kz.zzhalelov.sharemate.server.exception.NotFoundException;
import kz.zzhalelov.sharemate.server.request.dto.RequestMapper;
import kz.zzhalelov.sharemate.server.user.User;
import kz.zzhalelov.sharemate.server.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;

    public RequestServiceImpl(RequestRepository requestRepository,
                              UserRepository userRepository,
                              RequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.requestMapper = requestMapper;
    }

    @Override
    public Request create(int requesterId, Request request) {
        if (request.getDescription() == null || request.getDescription().isBlank()) {
            throw new BadRequestException("Описание не заполнено");
        }
        User requester = userRepository.findById(requesterId).orElseThrow();
        request.setRequester(requester);
        return requestRepository.save(request);
    }

    @Override
    public Request findById(int requesterId, int requestId) {
        return null;
    }

    @Override
    public Request findById(int requestId) {
        return requestRepository.findById(requestId).orElseThrow();
    }

    @Override
    public List<Request> findAllRequestsExceptMine(int requesterId) {
        User user = userRepository.findById(requesterId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return requestRepository.findAllByRequesterIdNotOrderByCreatedDesc(user.getId())
                .stream()
                .toList();
    }

    @Override
    public List<Request> findAllMyRequests(int requesterId) {
        User user = userRepository.findById(requesterId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return requestRepository.findAllByRequesterIdOrderByCreatedDesc(user.getId())
                .stream()
                .toList();
    }
}