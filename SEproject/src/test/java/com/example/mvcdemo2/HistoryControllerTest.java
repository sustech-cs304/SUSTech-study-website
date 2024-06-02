package com.example.mvcdemo2;

import com.example.mvcdemo2.controller.HistoryController;
import com.example.mvcdemo2.model.History;
import com.example.mvcdemo2.repository.AdminRepository;
import com.example.mvcdemo2.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class HistoryControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private HistoryController historyController;

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private AdminRepository adminRepository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(historyController).build();
        historyController.setUsrName("testUser");
    }

    @Test
    public void testGetUserName() throws Exception {
        mockMvc.perform(get("/get_user_name"))
                .andExpect(status().isOk())
                .andExpect(content().string("testUser"));
    }

    @Test
    public void testFindAllHistory() throws Exception {
        History history1 = new History();
        History history2 = new History();
        List<History> historyList = Arrays.asList(history1, history2);

        when(historyRepository.findAllHistory()).thenReturn(historyList);

        mockMvc.perform(get("/his"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testFindHistoryByUsername() throws Exception {
        History history = new History();
        List<History> historyList = Arrays.asList(history);

        when(historyRepository.findByUsername("testUser")).thenReturn(historyList);

        mockMvc.perform(get("/hisname"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testFindHistoryByUsernameAndID() throws Exception {
        History history = new History();
        List<History> historyList = Arrays.asList(history);

        when(adminRepository.findAdmin("testUser")).thenReturn(null);
        when(historyRepository.findHisByNameAndID("testUser", 1)).thenReturn(historyList);

        mockMvc.perform(get("/api/quiz-history")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("historyList", hasSize(1)))
                .andExpect(view().name("history"));
    }

    @Test
    public void testSaveAnswers() throws Exception {
        mockMvc.perform(post("/save-answers")
                        .param("answers", "testAnswers")
                        .param("quizId", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is("/selection")));
    }
}
