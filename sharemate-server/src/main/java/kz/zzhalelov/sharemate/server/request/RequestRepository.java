package kz.zzhalelov.sharemate.server.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findAllByRequesterIdOrderByCreatedDesc(int requesterId);

    List<Request> findAllByRequesterIdNotOrderByCreatedDesc(int requesterId, Pageable pageable);
}