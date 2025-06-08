package kz.zzhalelov.sharemate.server.user;

import jakarta.persistence.*;
import kz.zzhalelov.sharemate.server.request.Request;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    List<Request> requests;
}