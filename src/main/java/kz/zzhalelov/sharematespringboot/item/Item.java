package kz.zzhalelov.sharematespringboot.item;

import kz.zzhalelov.sharematespringboot.user.User;
import lombok.Data;

@Data
public class Item {
    private Integer id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
}