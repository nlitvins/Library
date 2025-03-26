INSERT INTO authors(id, name, second_name)
VALUES (1, 'Mike', 'Louis'),
       (2, 'John', 'Lock'),
       (3, 'Jack', 'Trans'),
       (4, 'Ivan', 'Tolstoy');

INSERT INTO books(id, title, author_id, quantity)
VALUES (1, 'Fiona', 1, 1),
       (2, 'Shrek', 4, 2),
       (3, 'Lord Farquaad', 3, 3),
       (4, 'Prince Charming', 2, 0),
       (5, 'Korotkij mech', 4, 12);

INSERT INTO users(id, name, second_name, user_name, password, email, mobile_number, person_code)
VALUES (1, 'John', 'Doe', 'johndoe', 'password123', 'johndoe@example.com', '20001111', '190201-27314'),
       (2, 'Jane', 'Smith', 'janesmith', 'securePass2024', 'janesmith@example.com', '20001112', '190202-27315'),
       (3, 'Alice', 'Johnson', 'alicejohnson', 'alicePass1', 'alicej@example.com', '20001113', '190203-27316'),
       (4, 'Bob', 'Williams', 'bobwilliams', 'bobSecure2024', 'bobw@example.com', '20001114', '190204-27317');

-- INSERT INTO reservations(id, user_id, book_id, created_date, term_date, status, extension_count, updated_date)
-- VALUES (1, 2, 3, '2025-03-05 14:30:00', '2025-03-06 14:30:00', 1, 2, '2025-03-05 14:30:00'),
--        (2, 1, 4, '2025-03-07 14:30:00', '2025-03-08 14:30:00', 2, 3, '2025-03-07 14:30:00'),
--        (3, 4, 2, '2025-03-10 14:30:00', '2025-03-11 14:30:00', 1, 4, '2025-03-10 14:30:00'),
--        (4, 3, 1, '2025-03-12 14:30:00', '2025-03-13 14:30:00', 3, 5, '2025-03-12 14:30:00');
