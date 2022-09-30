package ru.acme.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.acme.web.model.Content;
import ru.acme.web.repository.ContentRepository;
import ru.acme.web.repository.MenuRepository;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContentService {

    private final ContentRepository contentRepository;
    private final MenuRepository menuRepository;
    private final ImageService imageService;

    public ContentService(ContentRepository contentRepository, MenuRepository menuRepository, ImageService imageService) {
        this.contentRepository = contentRepository;
        this.menuRepository = menuRepository;
        this.imageService = imageService;
    }

    public List<Content> getAll() {
        return contentRepository.findAll(Sort.by(Sort.Direction.DESC, "creation"));
    }

    public List<Content> getAllActive() {
        return contentRepository.findAllForMain();
    }

    public List<Content> getFilterActive(String menu) {
        return contentRepository.getFilterMenuForMain(menu);
    }

    public Page<Content> getPage(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("creation").descending());
        return contentRepository.findAll(pageRequest);
    }

    public Long getLength() {
        return contentRepository.count();
    }

    public Content get(long contentId) {
        return contentRepository.findById(contentId).orElse(null);
    }

    public Content save(Content content) throws IOException {
        assert content != null;
        Long id = content.getId();
        content.setCreation(ZonedDateTime.now());
        String menuId = content.getMenuId();
        if (menuId != null) {
            content.setMenu(menuRepository.findById(menuId).orElse(null));
        }
        if (id == null) {
            content.setImgUrl(imageService.save(content.getNewImg(), getNewImgName(content)));
            return contentRepository.save(content);
        }

        Content oldContent = get(id);
        if (oldContent == null) return null;
        if (content.getNewImg() != null) {
            content.setImgUrl(imageService.save(content.getNewImg(), getNewImgName(content)));
            imageService.deleteImgFolder(oldContent.getImgUrl());
        } else {
            content.setImgUrl(oldContent.getImgUrl());
        }
        return contentRepository.save(content);
    }

    public boolean delete(Long contentId) throws IOException {
        Content oldContent = get(contentId);
        if (oldContent != null) {
            imageService.deleteImgFolder(oldContent.getImgUrl());
            contentRepository.deleteById(contentId);
            return true;
        }
        return false;
    }

    public int deleteList(List<Long> removeAll) throws IOException {
        List<Content> contents = new ArrayList<>();
        List<Long> removeIDs = new ArrayList<>();
        for (Long contentId : removeAll) {
            Content content = get(contentId);
            if (content != null) {
                contents.add(content);
                removeIDs.add(contentId);
            }
        }
        if (!contents.isEmpty()) {
            imageService.deleteListAll(contents);
            contentRepository.deleteAllById(removeIDs);
            return contents.size();
        }
        return 0;
    }

    private String getNewImgName(Content content) {
        return "usr" + content.getAuthorId() +
                "cr" + content.getCreation().toInstant().toEpochMilli();
    }
}