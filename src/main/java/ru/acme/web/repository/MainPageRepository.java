package ru.acme.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.acme.web.model.MainPage;

public interface MainPageRepository extends JpaRepository<MainPage, Long> {
}
