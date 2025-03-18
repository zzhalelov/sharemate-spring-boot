package kz.zzhalelov.sharematespringboot.item;

import jakarta.persistence.*;
import kz.zzhalelov.sharematespringboot.comment.Comment;
import kz.zzhalelov.sharematespringboot.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Column(name = "is_available")
    private Boolean available;
    @ManyToOne(cascade = CascadeType.ALL)
    private User owner;
    @OneToMany(mappedBy = "item")
    List<Comment> comments;
}