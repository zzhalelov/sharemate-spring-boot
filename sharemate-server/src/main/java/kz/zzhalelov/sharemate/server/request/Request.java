package kz.zzhalelov.sharemate.server.request;

import jakarta.persistence.*;
import kz.zzhalelov.sharemate.server.item.Item;
import kz.zzhalelov.sharemate.server.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User requester;
    private String description;
    @Column(name = "created_at")
    private LocalDateTime created;
    @OneToMany(mappedBy = "request")
    private List<Item> items;
}