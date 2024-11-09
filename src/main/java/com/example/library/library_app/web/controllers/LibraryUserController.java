package com.example.library.library_app.web.controllers;

import com.example.library.library_app.application.LibraryUserService;
import com.example.library.library_app.web.dto.LibraryUserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

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
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody LibraryUserDTO libraryUserDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

        var createdUserDTO = LibraryUserDTO.fromEntity(libraryUserService.create(LibraryUserDTO.toEntity(libraryUserDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody LibraryUserDTO libraryUserDTO, BindingResult result) {
        if (isNull(libraryUserDTO.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id is required");
        }

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

        var updatedUserDTO = LibraryUserDTO.fromEntity(libraryUserService.update(LibraryUserDTO.toEntity(libraryUserDTO)));
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUserDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        libraryUserService.delete(id);
    }
}
