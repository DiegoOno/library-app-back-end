CREATE TABLE IF NOT EXISTS library_user (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    register_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    phone VARCHAR(20) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_library_user_email ON library_user(email);

CREATE TABLE IF NOT EXISTS book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL UNIQUE,
    release_date TIMESTAMP NOT NULL,
    category VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS loan (
    id SERIAL PRIMARY KEY,
    library_user_id INTEGER NOT NULL REFERENCES library_user(id) ON DELETE CASCADE,
    book_id INTEGER NOT NULL REFERENCES book(id) ON DELETE CASCADE,
    loan_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    return_date TIMESTAMP NOT NULL,
    status VARCHAR(255) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_loan_library_user ON loan(library_user_id);
CREATE INDEX IF NOT EXISTS idx_loan_book ON loan(book_id);
