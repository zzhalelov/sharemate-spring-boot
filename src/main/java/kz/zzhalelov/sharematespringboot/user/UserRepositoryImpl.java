package kz.zzhalelov.sharematespringboot.user;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final Map<Integer, User> userMap = new HashMap<>();
    private int counter = 1;

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(getUniqueId());

        }
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(int userId) {
        return Optional.ofNullable(userMap.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public void delete(int id) {
        userMap.remove(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userMap.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    private int getUniqueId() {
        return counter++;
    }
}