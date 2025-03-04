package kz.zzhalelov.sharematespringboot.user;

import kz.zzhalelov.sharematespringboot.exception.ConflictException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new ConflictException("Пользователь с таким email существует");
        }
        return userRepository.save(user);
    }

    @Override
    public User update(int userId, User updatedUser) {
        Optional<User> optionalUser = userRepository.findByEmail(updatedUser.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getId() != userId) {
                throw new ConflictException("Пользователь с таким email существует");
            }
        }
        User existingUser = findById(userId);
        merge(existingUser, updatedUser);
        return userRepository.save(existingUser);
    }

    @Override
    public User findById(int userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public void delete(int userId) {
        userRepository.delete(userId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    private void merge(User existingUser, User updatedUser) {
        if (updatedUser.getName() != null && !updatedUser.getName().isBlank()) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {
            existingUser.setEmail(updatedUser.getEmail());
        }
    }
}