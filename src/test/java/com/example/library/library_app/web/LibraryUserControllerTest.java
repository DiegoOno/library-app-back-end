package com.example.library.library_app.web;

import com.example.library.library_app.application.LibraryUserService;
import com.example.library.library_app.domain.entity.LibraryUser;
import com.example.library.library_app.web.controllers.LibraryUserController;
import com.example.library.library_app.web.dto.LibraryUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibraryUserController.class)
public class LibraryUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryUserService libraryUserService;

    @InjectMocks
    private LibraryUserController libraryUserController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testCreateLibraryUser() throws Exception {
        var registerDate = LocalDateTime.now();
        registerDate = registerDate.truncatedTo(ChronoUnit.SECONDS);

        LibraryUserDTO libraryUserDTO = new LibraryUserDTO();
        libraryUserDTO.setId(1L);
        libraryUserDTO.setName("John Doe");
        libraryUserDTO.setEmail("john.doe@example.com");
        libraryUserDTO.setPhone("9636165718");
        libraryUserDTO.setRegisterDate(registerDate);

        LibraryUser libraryUser = new LibraryUser();
        libraryUser.setId(1L);
        libraryUser.setName("John Doe");
        libraryUser.setEmail("john.doe@example.com");
        libraryUser.setPhone("4824887688");
        libraryUser.setRegisterDate(registerDate);

        when(libraryUserService.create(any(LibraryUser.class))).thenReturn(libraryUser);

        mockMvc.perform(post("/library-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libraryUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phone").value("4824887688"))
                .andExpect(jsonPath("$.registerDate").value(registerDate.truncatedTo(ChronoUnit.SECONDS).toString()));

        verify(libraryUserService, times(1)).create(any(LibraryUser.class));
    }

    @Test
    void testFailCreateLibraryUser() throws Exception {
        LibraryUserDTO libraryUserDTO = new LibraryUserDTO();
        libraryUserDTO.setId(1L);
        libraryUserDTO.setName("John Doe");
        libraryUserDTO.setEmail("john.doe@example.com");
        libraryUserDTO.setPhone("123-456-7890");
        libraryUserDTO.setRegisterDate(LocalDateTime.now());

        when(libraryUserService.create(any(LibraryUser.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data"));

        mockMvc.perform(post("/library-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libraryUserDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid data"));
    }

}
