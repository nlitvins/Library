CREATE TABLE books
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(100) NOT NULL,
    author     VARCHAR(100) NOT NULL,
    isBorrowed BOOLEAN DEFAULT FALSE
);