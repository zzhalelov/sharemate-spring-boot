package kz.zzhalelov.sharematespringboot.user;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String email;
}