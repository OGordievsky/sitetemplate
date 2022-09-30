package ru.acme.web.data;

import ru.acme.web.model.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TestData {

    public static final MainPage TEST_MAIN_PAGE = new MainPage(
            "Вводная часть о компании",
            List.of("/img/0main/400w.jpeg", "/img/0main/800w.jpeg", "/img/0main/1500w.jpeg"),
            null,
            new HashMap<>() {{
                put("footerBg", "#0c0c0c");
                put("menuColor", "#232730");
            }},
            "142701, обл. Московская, г. Видное, ул. Радужная, 10", List.of("+7-910-449-05-67"),
            "adamov.m@acme-eg.ru",
            new HashMap<>() {{
                put("telegram", "mvadamov");
            }});
    public static final User test_admin = new User(1L, "admin_test", "admin_test", "admin_test@gmail.com",
            "{noop}admin_test", true, "1", Set.of(Role.ADMIN, Role.USER));

    public static final List<Content> testContentList = new ArrayList<>();

    public static final Menu menu1 = new Menu("О нас");

    public static final Menu menu2 = new Menu("Продукты");

    public static final Menu menu3 = new Menu("Проекты");

    public static final List<Menu> MENU = List.of(menu1, menu2, menu3);

    static {
        for (int i = 0; i < MENU.size(); i++) {
            MENU.get(i).setMenu(String.valueOf(i));
        }
        TEST_MAIN_PAGE.setMenuList(MENU);
    }

    public static final List<Menu> NEW_MENU = List.of(new Menu("О нас2"), new Menu("Продукты2"), new Menu("Проекты2"));
    public static final List<String> NEW_MENU_STRING = List.of("О нас2", "Продукты2", "Проекты2");

    static {
        for (int i = 0; i < NEW_MENU.size(); i++) {
            NEW_MENU.get(i).setMenu(i + "new");
        }
    }

    public static final List<String> IMG_URLS = List.of("/img/0content/400w.jpeg", "/img/0content/800w.jpeg", "/img/0content/1500w.jpeg");

    public static final Content cont_1 = new Content(1L, MENU.get(0), "title1", "TEXT", IMG_URLS,
            ZonedDateTime.now(), test_admin, 7, true);

    public static final Content cont_2 = new Content(2L, MENU.get(1), "Product1", "TEXT", IMG_URLS,
            ZonedDateTime.now(), test_admin, 8, false);

    public static final Content new_cont = new Content(null, MENU.get(1), "ProductNew", "TEXT", IMG_URLS,
            ZonedDateTime.now(), test_admin, 8, false);

    public static final Content updated_cont = new Content(100L, MENU.get(1), "ProductUpdated", "TEXT", IMG_URLS,
            ZonedDateTime.now(), test_admin, 8, false);


    public static final Content invalid_cont = new Content(200L, MENU.get(1), "ProductUpdated", null, IMG_URLS,
            ZonedDateTime.now(), test_admin, 8, false);

    static {
        cont_1.setMenuId("menuId");
        testContentList.add(cont_1);
        testContentList.add(cont_2);
    }
}
