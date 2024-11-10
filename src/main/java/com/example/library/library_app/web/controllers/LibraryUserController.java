package com.example.library.library_app.web.controllers;

import com.example.library.library_app.application.LibraryUserService;
import com.example.library.library_app.domain.entity.LibraryUser;
import com.example.library.library_app.web.dto.LibraryUserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/library-user")
public class LibraryUserController {
    private final LibraryUserService libraryUserService;

    public LibraryUserController(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @GetMapping
    public List<LibraryUserDTO> findAll() {
        return libraryUserService.findAll().stream()
                .map(LibraryUserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibraryUserDTO> findById(@PathVariable("id") Long id) {
        return libraryUserService.findById(id)
                .map(libraryUser -> ResponseEntity.ok(LibraryUserDTO.fromEntity(libraryUser)))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "LibraryUser not found with id " + id
                ));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody LibraryUserDTO libraryUserDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

        LibraryUser createdUser = libraryUserService.create(LibraryUserDTO.toEntity(libraryUserDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(LibraryUserDTO.fromEntity(createdUser));
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody LibraryUserDTO libraryUserDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

        LibraryUser updatedUser = libraryUserService.update(LibraryUserDTO.toEntity(libraryUserDTO));
        return ResponseEntity.status(HttpStatus.OK).body(LibraryUserDTO.fromEntity(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            libraryUserService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id " + id);
        }
    }
}
