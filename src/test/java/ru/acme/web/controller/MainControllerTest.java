package ru.acme.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.acme.web.service.MainPageService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.acme.web.data.TestData.TEST_MAIN_PAGE;

class MainControllerTest extends AbstractControllerTest {

    @MockBean
    MainPageService mainPageService;

    @BeforeEach
    void setMockReturnValues() {
        Mockito.when(mainPageService.get())
                .thenReturn(TEST_MAIN_PAGE);

    }

    @Test
    void getMainPage() throws Exception {
        perform(MockMvcRequestBuilders
                .get("/main"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}