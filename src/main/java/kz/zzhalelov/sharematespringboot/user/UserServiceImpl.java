package kz.zzhalelov.sharematespringboot.user;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(int userId, User updatedUser) {
        return userRepository.save(updatedUser);
    }

    @Override
    public User findById(int userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public void delete(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}