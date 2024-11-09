package com.example.library.library_app.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "library_user")
public class LibraryUser {
    public LibraryUser() {}

    public LibraryUser(String name, String email, LocalDateTime registerDate, String phone) {
        this.name = name;
        this.email = email;
        this.registerDate = registerDate;
        this.phone = phone;
    }

    public LibraryUser(Long id, String name, String email, LocalDateTime registerDate, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.registerDate = registerDate;
        this.phone = phone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Name is required")
    private String name;

    @Column(name = "email")
    @Email(message = "Invalid email format")
    @NotNull(message = "Email is required")
    private String email;

    @Column(name = "register_date")
    @NotNull(message = "Register date is required")
    @PastOrPresent(message = "Register date cannot be in the future")
    private LocalDateTime registerDate;

    @Column(name = "phone", nullable = false)
    @NotNull(message = "Phone number is required")
    private String phone;

    @OneToMany(mappedBy = "libraryUser", cascade = CascadeType.ALL)
    private List<Loan> loans = new ArrayList<>();;

    public void mergeForUpdate(LibraryUser libraryUser) {
        this.name = libraryUser.getName();
        this.email = libraryUser.getEmail();
        this.registerDate = libraryUser.getRegisterDate();
        this.phone = libraryUser.getPhone();
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

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
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

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
