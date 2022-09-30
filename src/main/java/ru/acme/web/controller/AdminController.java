package ru.acme.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.acme.web.model.*;
import ru.acme.web.service.ContentService;
import ru.acme.web.service.MainPageService;
import ru.acme.web.service.UserService;
import ru.acme.web.util.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Validated
@RestController
@RequestMapping("/adminpanel")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private ContentService contentService;
    private MainPageService mainPageService;
    private UserService userService;

    public AdminController(ContentService contentService, MainPageService mainPageService, UserService userService) {
        this.contentService = contentService;
        this.mainPageService = mainPageService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<Long> login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if (user.getAuthorities().contains(Role.ADMIN)) {
            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ObjectNode>> getAll(
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "size", required = false) String size) {
        List<Content> contentList;
        if (page != null && size != null) {
            contentList = contentService.getPage(Integer.parseInt(page) - 1, Integer.parseInt(size)).getContent();
        } else {
            contentList = contentService.getAll();
        }
        return new ResponseEntity<>(contentList
                .stream()
                .map(content -> {
                    ObjectNode jsonNode = JsonObject.getJsonIgnoreFields(content, "content");
                    jsonNode.put("dateTime", content.getDateTime());
                    return jsonNode;
                })
                .toList(),
                HttpStatus.OK);
    }

    @GetMapping("/getLength")
    public ResponseEntity<Long> getLength() {
        return new ResponseEntity<>(contentService.getLength(), HttpStatus.OK);
    }

    @GetMapping("/content")
    public ResponseEntity<Content> get(@RequestParam(value = "id") String id) {
        if (!id.isEmpty()) {
            long contentId = Long.parseLong(id);
            Content content = contentService.get(contentId);
            if (content != null) {
                return new ResponseEntity<>(content, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/content")
    public ResponseEntity<Content> save(@Valid @RequestBody Content content) throws IOException {
        if (content != null && content.getAuthorId() != null) {
            User author = userService.get(content.getAuthorId());
            if (author != null && author.getRoles().contains(Role.ADMIN)) {
                content.setAuthor(author);
            } else {
                throw new IOException("Not allowed operation for this user");
            }
        }
        Content response = contentService.save(content);
        if (response == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @DeleteMapping("/content")
    public ResponseEntity<?> delete(
            @RequestParam(value = "id", required = false) String id,
            @RequestBody(required = false) JsonNode node
    ) throws IOException {
        if (id != null && !id.isEmpty()) {
            long contentId = Long.parseLong(id);
            if (contentService.delete(contentId)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        if (node != null && node.has("removeIds")) {
            ArrayNode arrayNode = (ArrayNode) node.get("removeIds");
            Iterator<JsonNode> iterator = arrayNode.elements();
            List<Long> removeIds = new ArrayList<>();
            while (iterator.hasNext()) {
                removeIds.add(Long.parseLong(iterator.next().asText()));
            }
            int deleted = contentService.deleteList(removeIds);
            if (deleted > 0) {
                return new ResponseEntity<>("Deleted: " + deleted, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/main")
    public ResponseEntity<MainPage> updateMain(@Valid @RequestBody MainPage mainPage) throws IOException {
        return new ResponseEntity<>(mainPageService.save(mainPage), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Map<String, String>> handleJsonProcessingException(JsonProcessingException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getLocation().sourceDescription(), ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, String>> defaultErrorHandler(HttpServletRequest req, Exception e) {
        Map<String, String> errors = new HashMap<>();
        errors.put(e.getMessage(), req.getRequestURI());
        return new ResponseEntity<>(errors, HttpStatus.METHOD_NOT_ALLOWED);
    }
}