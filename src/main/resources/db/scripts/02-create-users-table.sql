CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    second_name   VARCHAR(100) NOT NULL,
    user_name     VARCHAR(100) NOT NULL,
    password      VARCHAR(100) NOT NULL,
    email         VARCHAR(100) NOT NULL,
    mobile_number INTEGER      NOT NULL,
    person_code   VARCHAR(12)  NOT NULL
);
