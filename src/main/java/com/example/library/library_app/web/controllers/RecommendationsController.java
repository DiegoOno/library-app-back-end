package com.example.library.library_app.web.controllers;

import com.example.library.library_app.application.RecommendationService;
import com.example.library.library_app.web.dto.BookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {
    private RecommendationService recommendationService;

    public RecommendationsController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{libraryUserId}")
    public ResponseEntity<?> getRecommendations(@PathVariable("libraryUserId") Long libraryUserId) {
        if (isNull(libraryUserId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Library user id is required");
        }

        var booksDTOs = recommendationService.getRecommendations(libraryUserId).stream()
                .map(BookDTO::fromEntity)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(booksDTOs);
    }
}
