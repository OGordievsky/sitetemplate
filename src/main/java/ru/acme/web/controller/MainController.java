package ru.acme.web.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.acme.web.model.Content;
import ru.acme.web.model.MainPage;
import ru.acme.web.service.MainPageService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class MainController {

    MainPageService mainPageService;

    public MainController(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }

    @GetMapping
    public ResponseEntity<MainPage> getMainPage() {
        return new ResponseEntity<>(mainPageService.get(), HttpStatus.OK);
    }

    @GetMapping("/content")
    public ResponseEntity<Map<String, List<Content>>> getContent() {
        return new ResponseEntity<>(mainPageService.getContentMap(), HttpStatus.OK);
    }
}
