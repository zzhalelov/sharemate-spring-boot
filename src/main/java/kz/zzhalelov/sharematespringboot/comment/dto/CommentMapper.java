package kz.zzhalelov.sharematespringboot.comment.dto;

import kz.zzhalelov.sharematespringboot.comment.Comment;
import kz.zzhalelov.sharematespringboot.item.Item;
import kz.zzhalelov.sharematespringboot.user.User;
import kz.zzhalelov.sharematespringboot.user.dto.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public Comment fromCreate(CommentCreateDto commentCreateDto) {
        Comment comment = new Comment();
        comment.setText(commentCreateDto.getText());
        return comment;
    }

    public CommentResponseDto toResponse(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setAuthorName(comment.getAuthor().getName());
        dto.setCreated((comment.getCreatedAt()));
        return dto;
    }
}