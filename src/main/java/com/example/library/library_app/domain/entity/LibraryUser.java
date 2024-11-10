package com.example.library.library_app.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

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
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "libraryUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Loan> loans = new ArrayList<>();

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
