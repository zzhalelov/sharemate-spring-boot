package kz.zzhalelov.sharemate.server.request;

import java.util.List;

public interface RequestService {
    Request create(int requesterId, Request request);

    Request findById(int requesterId, int requestId);

    Request findById(int requestId);

    List<Request> findAllRequestsExceptMine(int requesterId);

    List<Request> findAllMyRequests(int requesterId);
}