package com.example.mvcdemo2;

import com.example.mvcdemo2.controller.QuizController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QuizController.class)
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowQuizWithoutQuizId() throws Exception {
        mockMvc.perform(get("/quiz"))
                .andExpect(status().isOk())
                .andExpect(view().name("quiz"))
                .andExpect(model().attributeDoesNotExist("quizId"));
    }

    @Test
    public void testShowQuizWithQuizId() throws Exception {
        mockMvc.perform(get("/quiz").param("id", "123"))
                .andExpect(status().isOk())
                .andExpect(view().name("quiz"))
                .andExpect(model().attribute("quizId", "123"));
    }

    @Test
    public void testQuizSelection() throws Exception {
        mockMvc.perform(get("/selection"))
                .andExpect(status().isOk())
                .andExpect(view().name("quizSelection"));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
