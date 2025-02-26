package kz.zzhalelov.sharematespringboot.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(int userId);

    List<User> findAll();

    void deleteById(int id);

    Optional<User> findByEmail(String email);
}