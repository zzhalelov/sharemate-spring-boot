package kz.zzhalelov.sharemate.server.comment;

import kz.zzhalelov.sharemate.server.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByItem(Item item);
}