package ru.acme.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.acme.web.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, String> {
}
