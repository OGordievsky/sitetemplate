package ru.acme.web.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.acme.web.model.Content;
import ru.acme.web.service.ContentService;
import ru.acme.web.util.JsonObject;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.acme.web.data.TestData.cont_1;
import static ru.acme.web.data.TestData.testContentList;

class ContentControllerTest extends AbstractControllerTest {

    public static final String CONTENT = "/content/";

    @MockBean
    ContentService contentService;

    @BeforeEach
    void setMockReturnValues() {
        Mockito.when(contentService.getAllActive())
                .thenReturn(testContentList);
        Mockito.when(contentService.get(cont_1.getId()))
                .thenReturn(cont_1);
        Mockito.when(contentService.getFilterActive(cont_1.getMenuId()))
                .thenReturn(List.of(cont_1));
    }

    @Test
    void getAllPageContent() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders
                .get(CONTENT))
                .andExpect(status().isOk()).andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        String contentAsString = getContentAsString(resultActions);
        List list = JsonObject.readValue(contentAsString, List.class);
        Assertions.assertEquals(list.size(), testContentList.size());
    }

    @Test
    void getFilteredPageContent() throws Exception {
        String menuId = "menuId";
        ResultActions resultActions = perform(MockMvcRequestBuilders
                .get(CONTENT).param("menu", menuId))
                .andExpect(status().isOk()).andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        String contentAsString = getContentAsString(resultActions);
        List list = JsonObject.readValue(contentAsString, List.class);
        Assertions.assertEquals(list.size(), testContentList.stream().
                filter(content ->
                        (content.getMenuId() != null && content.getMenuId().equals(menuId))).count());
    }

    @Test
    void getContent() throws Exception {
        Long id = cont_1.getId();
        ResultActions resultActions = perform(MockMvcRequestBuilders
                .get(CONTENT + id))
                .andExpect(status().isOk()).andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        String contentAsString = getContentAsString(resultActions);
        Content content = JsonObject.readValue(contentAsString, Content.class);
        Assertions.assertEquals(cont_1, content);
    }

    @Test
    void getContentNotFound() throws Exception {
        perform(MockMvcRequestBuilders
                .get(CONTENT + "9999"))
                .andExpect(status().isNotFound());
    }
}