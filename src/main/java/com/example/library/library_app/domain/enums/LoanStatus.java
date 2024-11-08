package com.example.library.library_app.domain.enums;

public enum LoanStatus {
    ACTIVE,     // Represents an active loan (the book is currently loaned)
    COMPLETED,  // Represents a completed loan (the book has been returned)
    OVERDUE     // Represents a loan that is past its return date
}
