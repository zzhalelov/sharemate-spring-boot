package kz.zzhalelov.sharematespringboot.comment;

import jakarta.persistence.*;
import kz.zzhalelov.sharematespringboot.item.Item;
import kz.zzhalelov.sharematespringboot.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}