package ru.acme.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.acme.web.model.Content;
import ru.acme.web.service.ContentService;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {
    private ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public ResponseEntity<List<Content>> getPageContent(@RequestParam(value = "menu", required = false) String menu) {
        if (menu == null) {
            return new ResponseEntity<>(contentService.getAllActive(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(contentService.getFilterActive(menu), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> getContent(@PathVariable("id") String id) {
        Content content = contentService.get(Long.parseLong(id));
        if (content != null) {
            return new ResponseEntity<>(content,
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}