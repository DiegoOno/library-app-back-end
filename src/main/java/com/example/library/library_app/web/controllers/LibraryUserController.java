package com.example.library.library_app.web.controllers;

import com.example.library.library_app.application.LibraryUserService;
import com.example.library.library_app.domain.entity.LibraryUser;
import com.example.library.library_app.web.dto.LibraryUserDTO;
import org.springframework.http.HttpStatus;
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
    public LibraryUserDTO findById(@PathVariable("id") Long id) {
        return libraryUserService.findById(id)
                .map(LibraryUserDTO::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id " + id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public LibraryUserDTO create(@RequestBody LibraryUserDTO libraryUserDTO) {
        return LibraryUserDTO.fromEntity(libraryUserService.create(LibraryUserDTO.toEntity(libraryUserDTO)));
    }

    @PutMapping
    public LibraryUserDTO update(@RequestBody LibraryUserDTO libraryUserDTO) {
        return LibraryUserDTO.fromEntity(libraryUserService.update(LibraryUserDTO.toEntity(libraryUserDTO)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        libraryUserService.delete(id);
    }
}
