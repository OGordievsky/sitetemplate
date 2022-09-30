package ru.acme.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.acme.web.service.ImageService;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ImageControllerTest extends AbstractControllerTest {

    @MockBean
    ImageService service;

    @BeforeEach
    void setMockReturnValues() throws IOException {
        Mockito.when(service.get("someImg.jpeg","someFolder"))
                .thenReturn(new byte[]{1,2,3});

    }

    @Test
    void getImage() throws Exception {
        perform(MockMvcRequestBuilders
                .get("/img/someFolder/someImg.jpeg"))
                .andExpect(status().isOk()).andExpect(content()
                        .contentTypeCompatibleWith(MediaType.IMAGE_JPEG_VALUE));
    }
}