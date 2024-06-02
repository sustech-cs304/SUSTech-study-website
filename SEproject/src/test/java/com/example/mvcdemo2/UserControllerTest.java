package com.example.mvcdemo2;

import com.example.mvcdemo2.controller.HistoryController;
import com.example.mvcdemo2.controller.UserController;
import com.example.mvcdemo2.model.Users;
import com.example.mvcdemo2.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HistoryController historyController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUser() throws Exception {
        Users user1 = new Users(1, "user1", "password1");
        Users user2 = new Users(2, "user2", "password2");

        List<Users> allUsers = Arrays.asList(user1, user2);
        when(userRepository.findAllUsers()).thenReturn(allUsers);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("user1")))
                .andExpect(jsonPath("$[1].name", is("user2")));
    }

    @Test
    public void testFindByUsername() throws Exception {
        Users user = new Users(1, "testUser", "password");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        mockMvc.perform(get("/user/find").param("username", "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("testUser")));
    }

    @Test
    public void testFindByUsernameNotFound() throws Exception {
        when(userRepository.findByUsername("testUser")).thenReturn(null);

        mockMvc.perform(get("/user/find").param("username", "testUser"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userRepository.findByUsername("newUser")).thenReturn(null);

        mockMvc.perform(post("/user/create")
                        .param("username", "newUser")
                        .param("password", "newPassword")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is("/selection")));
    }

    @Test
    public void testCreateUserExists() throws Exception {
        Users user = new Users(1, "existingUser", "password");

        when(userRepository.findByUsername("existingUser")).thenReturn(user);

        mockMvc.perform(post("/user/create")
                        .param("username", "existingUser")
                        .param("password", "newPassword")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Username already exists")));
    }

    @Test
    public void testChangePassword() throws Exception {
        Users user = new Users(1, "testUser", "oldPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        mockMvc.perform(post("/user/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"oldPassword\":\"oldPassword\",\"newPassword\":\"newPassword\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testChangePasswordUserNotFound() throws Exception {
        when(userRepository.findByUsername("testUser")).thenReturn(null);

        mockMvc.perform(post("/user/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"oldPassword\":\"oldPassword\",\"newPassword\":\"newPassword\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("User not found")));
    }

    @Test
    public void testChangePasswordIncorrectOldPassword() throws Exception {
        Users user = new Users(1, "testUser", "oldPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(user);

        mockMvc.perform(post("/user/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser\",\"oldPassword\":\"wrongPassword\",\"newPassword\":\"newPassword\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Old password is incorrect")));
    }
}
