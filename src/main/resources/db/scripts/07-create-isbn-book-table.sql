CREATE TABLE isbn_book
(
    id             SERIAL PRIMARY KEY,
    title          VARCHAR(100) NOT NULL,
    authors        VARCHAR(100) NOT NULL,
    publishers     VARCHAR(100) NOT NULL,
    published_date VARCHAR(100) NOT NULL,
    cover_url      VARCHAR(100) NOT NULL
)