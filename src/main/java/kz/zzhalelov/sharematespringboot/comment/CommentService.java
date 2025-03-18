package kz.zzhalelov.sharematespringboot.comment;

import kz.zzhalelov.sharematespringboot.comment.dto.CommentCreateDto;
import kz.zzhalelov.sharematespringboot.item.Item;

import java.util.List;

public interface CommentService {
    Comment create(int itemId, int userId, Comment comment);

    List<Comment> findAllByItem(int itemId);
}