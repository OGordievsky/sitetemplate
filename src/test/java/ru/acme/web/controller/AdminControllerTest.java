package ru.acme.web.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.acme.web.model.Content;
import ru.acme.web.model.MainPage;
import ru.acme.web.service.ContentService;
import ru.acme.web.service.MainPageService;
import ru.acme.web.service.UserService;
import ru.acme.web.util.JsonObject;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.acme.web.data.TestData.*;

class AdminControllerTest extends AbstractControllerTest {

    public static final String ADMINPANEL = "/adminpanel/";

    @MockBean
    private UserService userService;

    @MockBean
    private ContentService contentService;

    @MockBean
    private MainPageService mainPageService;

    @BeforeEach
    void setMockReturnValues() throws IOException {
        Mockito.when(userService.loadUserByUsername(test_admin.getEmail()))
                .thenReturn(test_admin);
        Mockito.when(userService.get(test_admin.getId()))
                .thenReturn(test_admin);
        Mockito.when(contentService.getAll())
                .thenReturn(testContentList);
        Mockito.when(contentService.getPage(0, 1))
                .thenReturn(new PageImpl<>(testContentList));
        Mockito.when(contentService.get(cont_1.getId()))
                .thenReturn(cont_1);
        Mockito.when(contentService.save(new_cont))
                .thenReturn(new_cont);
        Mockito.when(contentService.save(updated_cont))
                .thenReturn(updated_cont);
        Mockito.when(contentService.deleteList(Mockito.anyList()))
                .thenAnswer((Answer<Integer>) invocation -> {
                    Object argument = invocation.getArgument(0);
                    return ((List<?>) argument).size();
                });
        Mockito.when(mainPageService.save(Mockito.any(MainPage.class)))
                .thenReturn(new MainPage());
    }

    @Test
    void getUnAuthorized() throws Exception {
        perform(MockMvcRequestBuilders
                .get(ADMINPANEL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAll() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders
                .get(ADMINPANEL + "getAll")
                .with(userHttpBasic(test_admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        String contentAsString = getContentAsString(resultActions);
        List list = JsonObject.readValue(contentAsString, List.class);
        Assertions.assertEquals(list.size(), testContentList.size());
    }

    @Test
    void getPage() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders
                .get(ADMINPANEL + "getAll")
                .param("page", "1")
                .param("size", "1")
                .with(userHttpBasic(test_admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        String contentAsString = getContentAsString(resultActions);
        List list = JsonObject.readValue(contentAsString, List.class);
        Assertions.assertEquals(list.size(), testContentList.size());
    }

    @Test
    void getWithEmptyId() throws Exception {
        perform(MockMvcRequestBuilders
                .get(ADMINPANEL + "content")
                .with(userHttpBasic(test_admin)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getWithNotExistedId() throws Exception {
        perform(MockMvcRequestBuilders
                .get(ADMINPANEL + "content")
                .with(userHttpBasic(test_admin))
                .param("id", "9999999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void get() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders
                .get(ADMINPANEL + "content")
                .with(userHttpBasic(test_admin))
                .param("id", cont_1.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        String contentAsString = getContentAsString(resultActions);
        Content content = JsonObject.readValue(contentAsString, Content.class);
        Assertions.assertEquals(content, cont_1);
    }

    @Test
    void create() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders
                .post(ADMINPANEL + "content")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(test_admin))
                .content(JsonObject.writeValue(new_cont)))
                .andExpect(status().isOk());
        String contentAsString = getContentAsString(resultActions);
        Content content = JsonObject.readValue(contentAsString, Content.class);
        Assertions.assertEquals(content, new_cont);
    }

    @Test
    void update() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders
                .post(ADMINPANEL + "content")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(test_admin))
                .content(JsonObject.writeValue(updated_cont)))
                .andExpect(status().isOk());
        String contentAsString = getContentAsString(resultActions);
        Content content = JsonObject.readValue(contentAsString, Content.class);
        Assertions.assertEquals(content, updated_cont);
    }

    @Test
    void updateInvalid() throws Exception {
        perform(MockMvcRequestBuilders
                .post(ADMINPANEL + "content")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(test_admin))
                .content(JsonObject.writeValue(invalid_cont)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateWithNoContent() throws Exception {
        perform(MockMvcRequestBuilders
                .post(ADMINPANEL + "content")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(test_admin)))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteByNode() throws Exception {
        String ids2remove = "{\"removeIds\":[300,400]}";
        perform(MockMvcRequestBuilders
                .delete(ADMINPANEL + "content")
                .with(userHttpBasic(test_admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(ids2remove))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByNull() throws Exception {
        perform(MockMvcRequestBuilders
                .delete(ADMINPANEL + "content")
                .with(userHttpBasic(test_admin)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUpdateMain() throws Exception {
        String mainPageForeNode = "{\"title\":\"Современные инженерные решения\",\"imgUrl\":[\"data:image/jpeg;base64\"],\"colorScheme\":{\"sectionTitle\":\"#a55c30\",\"menuColor\":\"#ffffff\",\"slideTitle\":\"#232730\",\"footerBg\":\"#0c0c0c\",\"menuBg\":\"#0c0c0c\",\"introTitle\":\"#ffffff\"},\"address\":\"142701, обл. Московская, г. Видное, ул. Радужная, 10\",\"telList\":[\"+7-910-449-05-67\",\"+7-123-456-78-90\"],\"email\":\"adamov.m@acme-eg.ru\",\"socials\":{\"telegram\":\"mvadamov\"},\"newMenu\":[\"О нас\",\"Решения\",\"Услуги\"]}";
        perform(MockMvcRequestBuilders
                .post(ADMINPANEL + "main")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mainPageForeNode)
                .with(userHttpBasic(test_admin)))
                .andExpect(status().isOk());
    }
}