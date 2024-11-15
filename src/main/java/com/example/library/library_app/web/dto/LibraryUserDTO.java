package com.example.library.library_app.web.dto;

import com.example.library.library_app.domain.entity.LibraryUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryUserDTO {

    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    @Email
    private String email;

    @NotNull(message = "Register date is required")
    @PastOrPresent(message = "Register date cannot be in the future")
    private LocalDateTime registerDate;

    @NotNull(message = "Phone is required")
    private String phone;

    @JsonIgnore
    private List<LoanDTO> loans = new ArrayList<>();

    public LibraryUserDTO() {
    }

    public LibraryUserDTO(Long id, String name, String email, LocalDateTime registerDate, String phone, List<LoanDTO> loans) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registerDate = registerDate;
        this.phone = phone;
        this.loans = loans;
    }

    public static LibraryUserDTO fromEntity(LibraryUser libraryUser) {
        List<LoanDTO> loansDTOs = libraryUser.getLoans().stream()
                .map(LoanDTO::fromEntity)
                .collect(Collectors.toList());

        return new LibraryUserDTO(
                libraryUser.getId(),
                libraryUser.getName(),
                libraryUser.getEmail(),
                libraryUser.getRegisterDate(),
                libraryUser.getPhone(),
                loansDTOs
        );
    }

    public static LibraryUser toEntity(LibraryUserDTO libraryUserDTO) {

        return new LibraryUser(
                libraryUserDTO.getId(),
                libraryUserDTO.getName(),
                libraryUserDTO.getEmail(),
                libraryUserDTO.getRegisterDate(),
                libraryUserDTO.getPhone()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<LoanDTO> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanDTO> loans) {
        this.loans = loans;
    }
}
