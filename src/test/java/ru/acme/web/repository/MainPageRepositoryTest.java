package ru.acme.web.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.acme.web.model.MainPage;

import java.util.Optional;


class MainPageRepositoryTest extends AbstractRepository {

    @Autowired
    MainPageRepository repository;

    @Test
    void findById() {
        Optional<MainPage> mainPage = repository.findById(1L);
        Assertions.assertTrue(mainPage.isPresent());
        Assertions.assertEquals(mainPage.get().getId(), 1);
    }

    @Test
    void save() {
        Optional<MainPage> mainPage = repository.findById(1L);
        Assertions.assertTrue(mainPage.isPresent());
        MainPage page = mainPage.get();
        Assertions.assertEquals(page.getId(), 1);
        page.setEmail("updated.m@acme-eg.ru");
        repository.save(page);

        Optional<MainPage> updatedMainPage = repository.findById(1L);
        Assertions.assertTrue(updatedMainPage.isPresent());
        MainPage updatedPage = updatedMainPage.get();

        Assertions.assertEquals(updatedPage.getEmail(), "updated.m@acme-eg.ru");

    }
}