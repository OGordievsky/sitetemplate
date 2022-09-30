package ru.acme.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.acme.web.model.Content;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("SELECT c FROM Content c WHERE c.show IS TRUE AND c.menu IS NOT NULL ORDER BY c.priority DESC ")
    List<Content> findAllForMain();

    @Query("SELECT c FROM Content c WHERE c.menu.menu = ?1 AND c.show = true ORDER BY c.priority DESC")
    List<Content> getFilterMenuForMain(String menu);
}
