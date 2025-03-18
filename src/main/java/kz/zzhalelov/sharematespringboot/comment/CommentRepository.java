package kz.zzhalelov.sharematespringboot.comment;

import kz.zzhalelov.sharematespringboot.comment.dto.CommentResponseDto;
import kz.zzhalelov.sharematespringboot.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByItem(Item item);
}