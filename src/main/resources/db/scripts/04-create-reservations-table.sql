CREATE TABLE reservations
(
    id      SERIAL PRIMARY KEY,
    user_id INT      NOT NULL REFERENCES "users" (id),
    book_id INT      NOT NULL REFERENCES "books" (id),
    date    DATE     NOT NULL,
    term    DATE     NOT NULL,
    status  SMALLINT NOT NULL
);