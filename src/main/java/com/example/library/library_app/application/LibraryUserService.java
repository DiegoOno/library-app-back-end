package com.example.library.library_app.application;

import com.example.library.library_app.domain.entity.LibraryUser;
import com.example.library.library_app.domain.repository.LibraryUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryUserService {
    private final LibraryUserRepository libraryUserRepository;

    @Autowired
    public LibraryUserService(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    @Transactional(readOnly = true)
    public List<LibraryUser> findAll() {
        return libraryUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<LibraryUser> findById(Long id) {
        return libraryUserRepository.findById(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public LibraryUser create(LibraryUser libraryUser) {
        return libraryUserRepository.save(libraryUser);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public LibraryUser update(LibraryUser libraryUser) {
        LibraryUser user = libraryUserRepository.findById(libraryUser.getId()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found with id " + libraryUser.getId()
        ));
        user.mergeForUpdate(libraryUser);

        return libraryUserRepository.save(user);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Long id) {
        LibraryUser user = libraryUserRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id));
        libraryUserRepository.deleteById(user.getId());
    }
}
