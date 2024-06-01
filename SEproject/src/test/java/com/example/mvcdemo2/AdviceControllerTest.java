package com.example.mvcdemo2;

import com.example.mvcdemo2.controller.AdviceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AdviceController.class)
public class AdviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testShowAdvicePage() throws Exception {
        mockMvc.perform(get("/advice"))
                .andExpect(status().isOk())
                .andExpect(view().name("study_advice"));
    }

    @Test
    public void testShowPostPage() throws Exception {
        mockMvc.perform(get("/post"))
                .andExpect(status().isOk())
                .andExpect(view().name("new_post"));
    }

    @Test
    public void testShowNewPostPage() throws Exception {
        mockMvc.perform(get("/new_post"))
                .andExpect(status().isOk())
                .andExpect(view().name("new_post"));
    }
}
