CREATE TABLE books
(
    id            SERIAL PRIMARY KEY,
    title         VARCHAR(100)  NOT NULL,
    author        VARCHAR(1024) NOT NULL,
    quantity      INT DEFAULT NULL CHECK (quantity >= 0),
    creation_year DATE          NOT NULL,
    status        SMALLINT      NOT NULL,
    genre         SMALLINT      NOT NULL,
    pages         SMALLINT      NOT NULL,
    edition       VARCHAR(100)  NOT NULL,
    release_date  DATE          NOT NULL,
    type          SMALLINT      NOT NULL
);
