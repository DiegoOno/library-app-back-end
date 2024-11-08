package com.example.library.library_app.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "library_user")
public class LibraryUser {
    public LibraryUser() {}

    public LibraryUser(String name, String email, LocalDate registerDate, String phone) {
        this.name = name;
        this.email = email;
        this.registerDate = registerDate;
        this.phone = phone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "libraryUser", cascade = CascadeType.ALL)
    private List<Loan> loans;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
