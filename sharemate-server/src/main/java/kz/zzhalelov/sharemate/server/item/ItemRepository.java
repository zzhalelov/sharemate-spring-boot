package kz.zzhalelov.sharemate.server.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByOwner_IdOrderByIdAsc(int ownerId);

    @Query("select i from Item i " +
            "where i.available = true " +
            "and (upper(i.name) like upper(concat('%', :text, '%'))  " +
            "or upper(i.description) like upper(concat('%', :text, '%'))) ")
    List<Item> search(String text);
}