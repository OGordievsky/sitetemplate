package ru.acme.web.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import ru.acme.web.model.Content;
import ru.acme.web.model.MainPage;
import ru.acme.web.model.Menu;
import ru.acme.web.repository.ContentRepository;
import ru.acme.web.repository.MainPageRepository;
import ru.acme.web.repository.MenuRepository;

import java.io.IOException;
import java.util.*;

import static ru.acme.web.data.TestData.*;

class MainPageServiceTest extends AbstractServiceTest {

    @Autowired
    MainPageService service;

    @MockBean
    MainPageRepository pageRepository;

    @MockBean
    ContentRepository contentRepository;

    @MockBean
    MenuRepository menuRepository;

    @MockBean
    ImageService imageService;

    @BeforeEach
    void setMockReturnValues() {
        Mockito.when(pageRepository.findById(1L))
                .thenReturn(Optional.of(TEST_MAIN_PAGE));
        Mockito.when(menuRepository.findAll(Sort.by("queue")))
                .thenReturn(new ArrayList<>(MENU));
        Mockito.when(menuRepository.findAll())
                .thenReturn(new ArrayList<>(MENU));
        Mockito.when(menuRepository.saveAll(Mockito.anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(contentRepository.getFilterMenuForMain(Mockito.anyString()))
                .thenReturn(Collections.emptyList());
        Mockito.when(imageService.save(Mockito.anyString(), Mockito.anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(pageRepository.save(Mockito.any(MainPage.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void getContentMap() {
        Map<String, List<Content>> contentMap = service.getContentMap();
        System.out.println(contentMap);
        for (Menu menu : MENU) {
            Assertions.assertTrue(contentMap.containsKey(menu.getMenu()));
        }
    }

    @Test
    void updateMenu() {
        List<Menu> newMenu = new ArrayList<>(MENU);
        newMenu.remove(menu1);
        Menu aNew = new Menu("new");
        aNew.setMenu("new");
        newMenu.add(aNew);
        List<Menu> updated = service.updateMenu(newMenu);
        Assertions.assertEquals(updated, newMenu);
    }

    @Test
    void save() throws IOException {
        MainPage mainPage = new MainPage();
        List<String> menuAsString = NEW_MENU.stream().map(Menu::getName).toList();
        mainPage.setNewMenu(menuAsString);
        MainPage updated = service.save(mainPage);
        Assertions.assertEquals(menuAsString,
                updated.getMenuList().stream().map(Menu::getName).toList());
    }

    @Test
    void saveWithNullNewMenu() throws IOException {
        MainPage mainPage = new MainPage();
        MainPage updated = service.save(mainPage);
        Assertions.assertEquals(TEST_MAIN_PAGE.getMenuList(),
                updated.getMenuList());
    }
}