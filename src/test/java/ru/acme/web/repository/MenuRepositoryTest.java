package ru.acme.web.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.acme.web.model.Menu;

import java.util.List;
import java.util.Optional;

class MenuRepositoryTest extends AbstractRepository{

    @Autowired
    MenuRepository repository;

    @Test
    void findById() {
        String id = "o_nas";
        Optional<Menu> menu = repository.findById(id);
        Assertions.assertTrue(menu.isPresent());
        Assertions.assertEquals(id, menu.get().getMenu());
    }

    @Test
    void findAll() {
        List<Menu> all = repository.findAll();
        Assertions.assertNotNull(all);
        Assertions.assertEquals(3, all.size());
    }

    @Test
    void deleteAll() {
        String id = "o_nas";
        Optional<Menu> menu = repository.findById(id);
        Assertions.assertTrue(menu.isPresent());
        Assertions.assertEquals(id, menu.get().getMenu());

        List<Menu> ids2delete = List.of(menu.get());
        repository.deleteAll(ids2delete);
        menu = repository.findById(id);
        Assertions.assertTrue(menu.isEmpty());
    }

    @Test
    void saveAll() {
        String id = "o_nas";
        Optional<Menu> menu = repository.findById(id);
        Assertions.assertTrue(menu.isPresent());
        Assertions.assertEquals(id, menu.get().getMenu());

        Menu newMenu = menu.get();
        newMenu.setMenu("new title");
        List<Menu> menus = repository.saveAll(List.of(newMenu));
        Assertions.assertEquals(menus.get(0), newMenu);

        List<Menu> all = repository.findAll();
        Assertions.assertEquals(4, all.size());
    }

    @Test
    void saveWithDuplicatedId() {
        String id = "o_nas";
        Optional<Menu> menu = repository.findById(id);
        Assertions.assertTrue(menu.isPresent());
        Assertions.assertEquals(id, menu.get().getMenu());

        Menu newMenu = menu.get();
        repository.saveAll(List.of(newMenu));
        List<Menu> all = repository.findAll();
        Assertions.assertEquals(3, all.size());
    }


}