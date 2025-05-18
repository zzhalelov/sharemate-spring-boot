package kz.zzhalelov.sharemate.server.comment;

import java.util.List;

public interface CommentService {
    Comment create(int itemId, int userId, Comment comment);

    List<Comment> findAllByItem(int itemId);
}