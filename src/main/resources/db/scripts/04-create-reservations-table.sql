CREATE TABLE reservations
(
    id      SERIAL PRIMARY KEY,
    user_id INT      NOT NULL REFERENCES "users" (id),
    book_id INT      NOT NULL REFERENCES "books" (id),
    created_date  TIMESTAMP NOT NULL,
    term_date       TIMESTAMP NOT NULL,
    status        SMALLINT  NOT NULL,
    extension_count SMALLINT,
    updated_date    TIMESTAMP NOT NULL
);