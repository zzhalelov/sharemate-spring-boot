package kz.zzhalelov.sharemate.server.user;

import java.util.List;

public interface UserService {
    User create(User user);

    User update(int userId, User updatedUser);

    User findById(int userId);

    void delete(int userId);

    List<User> findAll();
}