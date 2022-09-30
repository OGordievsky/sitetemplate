package ru.acme.web.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.acme.web.model.Content;
import ru.acme.web.model.MainPage;
import ru.acme.web.model.Menu;
import ru.acme.web.repository.ContentRepository;
import ru.acme.web.repository.MainPageRepository;
import ru.acme.web.repository.MenuRepository;
import ru.acme.web.repository.local.DefaultMainPage;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainPageService {

    private final MainPageRepository pageRepository;
    private final ContentRepository contentRepository;
    private final MenuRepository menuRepository;
    private final ImageService imageService;

    public MainPageService(MainPageRepository pageRepository, ContentRepository contentRepository, MenuRepository menuRepository, ImageService imageService) {
        this.pageRepository = pageRepository;
        this.contentRepository = contentRepository;
        this.menuRepository = menuRepository;
        this.imageService = imageService;
    }

    public MainPage get() {
        return pageRepository.findById(1L).orElse(DefaultMainPage.MAIN_PAIGE);
    }

    public Map<String, List<Content>> getContentMap() {
        Map<String, List<Content>> result = new LinkedHashMap<>();
        for (Menu menu : menuRepository.findAll(Sort.by("queue"))) {
            String menuKey = menu.getMenu();
            result.put(menuKey, contentRepository.getFilterMenuForMain(menuKey));
        }
        return result;
    }

    public MainPage save(MainPage mainPage) throws IOException {
        assert mainPage != null;
        MainPage oldPage = get();

        if (mainPage.getNewMenu() != null) {
            List<Menu> newMenu = mainPage.getNewMenu().stream().map(menuName -> new Menu(menuName, mainPage)).toList();
            mainPage.setMenuList(updateMenu(newMenu));
        } else {
            mainPage.setMenuList(oldPage.getMenuList());
        }

        if (mainPage.getNewImg() != null) {
            if (oldPage != null){
                imageService.deleteImgFolder(oldPage.getImgUrl());
            }
            mainPage.setImgUrl(imageService.save(mainPage.getNewImg(), "main" + new Date().getTime()));
        } else {
            mainPage.setImgUrl(oldPage.getImgUrl());
        }
        return pageRepository.save(mainPage);
    }

    public List<Menu> updateMenu(List<Menu> newMenu) {
        List<Menu> menu = menuRepository.findAll();
        List<Menu> remove = menu.stream().filter(om -> !newMenu.contains(om)).toList();
        if (!remove.isEmpty()) {
            menu.removeAll(remove);
            menuRepository.deleteAll(remove);
        }
        List<Menu> addMenu = newMenu.stream().filter(nm -> !menu.contains(nm)).toList();
        if (!addMenu.isEmpty()) {
            menu.addAll(addMenu);
        }
        menu.forEach(m -> m.setQueue(newMenu.indexOf(m)));
        return menuRepository.saveAll(menu);
    }
}