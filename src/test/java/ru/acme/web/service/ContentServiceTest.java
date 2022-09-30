package ru.acme.web.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.acme.web.model.Content;
import ru.acme.web.repository.ContentRepository;
import ru.acme.web.repository.MenuRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.acme.web.data.TestData.cont_1;

class ContentServiceTest extends AbstractServiceTest {

    @Autowired
    ContentService service;

    @MockBean
    ContentRepository contentRepository;

    @MockBean
    MenuRepository menuRepository;

    @MockBean
    ImageService imageService;

    @BeforeEach
    void setMockReturnValues() {
        Mockito.when(contentRepository.findById(cont_1.getId()))
                .thenReturn(Optional.of(cont_1));
        Mockito.when(contentRepository.save(Mockito.any(Content.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(imageService.save(Mockito.anyString(), Mockito.anyString()))
                .thenAnswer(invocation -> List.of("newImg"));
        Mockito.when(menuRepository.findById(cont_1.getMenuId()))
                .thenReturn(Optional.of(cont_1.getMenu()));
    }

    @Test
    void updateNotExisted() throws IOException {
        Content content = new Content();
        long notExistedId = 999L;
        content.setId(notExistedId);
        Assertions.assertNull(service.save(content));
    }

    @Test
    void update() throws IOException {
        Content content = new Content();
        content.setId(cont_1.getId());
        content.setMenuId(cont_1.getMenuId());
        List<String> newImgUrl = new ArrayList<>(cont_1.getImgUrl());
        newImgUrl.add("new ImUrl");
        content.setImgUrl(newImgUrl);
        Content updated = service.save(content);
        Assertions.assertEquals(updated.getId(), cont_1.getId());
        Assertions.assertEquals(updated.getMenu(), cont_1.getMenu());
        //no matter if adding smth to this list, updated value must return old imgUrls
        Assertions.assertEquals(updated.getImgUrl(), cont_1.getImgUrl());
    }

    @Test
    void create() throws IOException {
        Content content = new Content();
        content.setMenuId(cont_1.getMenuId());
        content.setNewImg("newImg");
        Content created = service.save(content);
        Assertions.assertEquals(created.getMenu(), cont_1.getMenu());
        Assertions.assertEquals(content.getImgUrl(), List.of("newImg"));
    }

    @Test
    void deleteByNull() throws IOException {
        long notExistedId = 999L;
        Assertions.assertFalse(service.delete(notExistedId));
    }

    @Test
    void delete() throws IOException {
        Assertions.assertTrue(service.delete(cont_1.getId()));
    }

    @Test
    void deleteByList() throws IOException {
        long notExistedId = 999L;
        List<Long> idsForContent2Remove = List.of(cont_1.getId(), notExistedId);
        int removed = service.deleteList(idsForContent2Remove);
        Assertions.assertEquals(removed, 1);
    }
}