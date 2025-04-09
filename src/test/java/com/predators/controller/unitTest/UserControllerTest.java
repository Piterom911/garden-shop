package com.predators.controller.unitTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predators.controller.UserController;
import com.predators.entity.Cart;
import com.predators.entity.Favorite;
import com.predators.entity.Product;
import com.predators.entity.User;
import com.predators.entity.enums.Role;
import com.predators.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void init() {
        user = new User(1L,
                "Test User",
                "testuser@mail.com",
                "+49-151-768-13-91",
                "password-hash",
                Role.CLIENT,
                null,
                new Cart()
        );
    }

    @Test
    public void createUser_ReturnCreated() throws Exception {
        given(userService.create(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void getAllUsers_ReturnsUserList() throws Exception {
        List<User> userList = Arrays.asList(user);

        given(userService.getAll()).willReturn(userList);

        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(userList.size()))
                .andExpect(jsonPath("$[0].name").value(user.getName()));
    }

    @Test
    public void getUserById_ReturnsUser() throws Exception {
        Long userId = 1L;
        given(userService.getById(userId)).willReturn(user);

        mockMvc.perform(get("/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    public void deleteUserById_ReturnsOk() throws Exception {
        Long userId = 1L;
        willDoNothing().given(userService).delete(userId);

        mockMvc.perform(delete("v1/users/{id}", userId))
                .andExpect(status().isOk());
    }
}