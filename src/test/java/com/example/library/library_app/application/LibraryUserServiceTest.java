package com.example.library.library_app.application;

import com.example.library.library_app.domain.entity.LibraryUser;
import com.example.library.library_app.domain.repository.LibraryUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LibraryUserServiceTest {
    @Autowired
    private LibraryUserService libraryUserService;

    @MockBean
    private LibraryUserRepository libraryUserRepository;

    @Test
    void testCreateLibraryUser() {
        var date = LocalDateTime.now();

        LibraryUser libraryUser = new LibraryUser();
        libraryUser.setId(1L);
        libraryUser.setName("John Doe");
        libraryUser.setEmail("john.doe@example.com");
        libraryUser.setPhone("8923168431");
        libraryUser.setRegisterDate(date);

        when(libraryUserRepository.save(any(LibraryUser.class))).thenReturn(libraryUser);

        LibraryUser result = libraryUserService.create(libraryUser);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("8923168431", result.getPhone());
        assertEquals(date, result.getRegisterDate());

        verify(libraryUserRepository, times(1)).save(any(LibraryUser.class));
    }

    @Test
    void testFailCreateLibraryUser() {
        LibraryUser libraryUser = new LibraryUser();
        when(libraryUserRepository.save(any(LibraryUser.class))).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> libraryUserService.create(libraryUser));
    }

    @Test
    void findAllSuccess() {
        var date = LocalDateTime.now();

        LibraryUser libraryUser = new LibraryUser();
        libraryUser.setName("John Doe");
        libraryUser.setEmail("john.doe@example.com");
        libraryUser.setPhone("8923168431");
        libraryUser.setRegisterDate(date);

        when(libraryUserRepository.findAll()).thenReturn(List.of(libraryUser));

        var result = libraryUserService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void findAllFail() {
        when(libraryUserRepository.findAll()).thenReturn(List.of());

        var result = libraryUserService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByIdSuccess() {
        var date = LocalDateTime.now();

        LibraryUser libraryUser = new LibraryUser();
        libraryUser.setId(1L);
        libraryUser.setName("John Doe");
        libraryUser.setEmail("john.doe@example.com");
        libraryUser.setPhone("8923168431");
        libraryUser.setRegisterDate(date);

        when(libraryUserRepository.findById(1L)).thenReturn(java.util.Optional.of(libraryUser));

        var result = libraryUserService.findById(1L)
                .orElse(null);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("8923168431", result.getPhone());
        assertEquals(date, result.getRegisterDate());
    }

    @Test
    void findByIdFail() {
        var date = LocalDateTime.now();

        LibraryUser libraryUser = new LibraryUser();
        libraryUser.setId(1L);
        libraryUser.setName("John Doe");
        libraryUser.setEmail("john.doe@example.com");
        libraryUser.setPhone("8923168431");
        libraryUser.setRegisterDate(date);

        libraryUserRepository.save(libraryUser);

        var result = libraryUserService.findById(0L)
                .orElse(null);

        assertNull(result);
    }

    @Test
    void deleteByIdSuccess() {
        var date = LocalDateTime.now();

        LibraryUser libraryUser = new LibraryUser();
        libraryUser.setId(1L);
        libraryUser.setName("John Doe");
        libraryUser.setEmail("john.doe@example.com");
        libraryUser.setPhone("8923168431");
        libraryUser.setRegisterDate(date);

        when(libraryUserRepository.save(any(LibraryUser.class))).thenReturn(libraryUser);
        when(libraryUserRepository.findById(1L)).thenReturn(Optional.of(libraryUser));

        LibraryUser result = libraryUserRepository.save(libraryUser);
        assertNotNull(result);

        libraryUserService.delete(result.getId());
        verify(libraryUserRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdFail() {
        Long id = 999L;

        when(libraryUserRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            libraryUserService.delete(id); // This should throw an exception
        }, "User not found with id " + id);

        verify(libraryUserRepository, times(1)).findById(id);
    }
}
