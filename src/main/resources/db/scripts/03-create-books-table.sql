CREATE TABLE books
(
    id       SERIAL PRIMARY KEY,
    title    VARCHAR(100) NOT NULL,
    author VARCHAR(1024) NOT NULL,
    quantity INT DEFAULT NULL CHECK (quantity >= 0)
);
