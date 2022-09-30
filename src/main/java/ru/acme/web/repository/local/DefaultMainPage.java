package ru.acme.web.repository.local;

import ru.acme.web.model.MainPage;

import java.util.HashMap;
import java.util.List;

public class DefaultMainPage {
    public static final MainPage MAIN_PAIGE = new MainPage(
            "Автоматизация и Диспетчеризация",
            List.of("/img/0main/400w.jpeg", "/img/0main/800w.jpeg", "/img/0main/1500w.jpeg"),
            List.of(),
            new HashMap<>() {{
                put("menuBg", "#0c0c0c");
                put("sectionTitle", "#a55c30");
                put("slideTitle", "#232730");
                put("introTitle", "#ffffff");
                put("menuColor", "#ffffff");
                put("footerBg", "#232f3e");
                put("sectionOddBg",  "#ffefba");
                put("sectionEvenBg", "#ece9e6");
            }},
            "142701, обл. Московская, г. Видное, ул. Радужная, 10",
            List.of("+7-910-449-05-67"),
            "adamov.m@acme-eg.ru",
            new HashMap<>() {{
                put("telegram", "https://t.me/mvadamov");
            }});
}
