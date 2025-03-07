CREATE TABLE books
(
    id       SERIAL PRIMARY KEY,
    title    VARCHAR(100) NOT NULL,
    author_id INT NOT NULL REFERENCES "authors" (id),
    quantity INT DEFAULT NULL CHECK (quantity >= 0)
);
