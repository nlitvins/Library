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

INSERT INTO users(id, name, second_name, user_name, password, email, mobile_number, person_code, role)
VALUES (1, 'Admin', '', 'admin', '$2a$10$ZIMxUCwbT1jT8B.WX3XktuEObuiroVtvd.pbMkZyU7bWxpsDuaSyi', 'admin@example.com',
        '21111111', '120871-27314', 3),
       (2, 'Librarian', '', 'librarian', '$2a$10$AHtuMWtnyqk/i.pAwHdzb.6rF2.Ej3BXbKQXx.zmq212pGHL6JsSi',
        'librarian@example.com', '21111111', '120871-27314', 2),
       (3, 'User', 'Test 1', 'testuser1', '$2a$10$SgYUkqw/zdCngdOVESfTJuPuTpnYJkjlVlmFSTHK4Zi5NOmRsMIYW',
        'alicej@example.com', '20001113', '190203-27316', 1),
       (4, 'User', 'Test 2', 'testuser2', '$2a$10$Ode4GZCepl4P17E1Ns3fxO1pINsUEMgZYUJVvQ9A0g5akb0IyXIOi',
        'bobw@example.com', '20001114', '190204-27317', 1),
       (5, 'User', 'Test 3', 'testuser3', '$2a$10$.bGhMkXjpCsjImI4ZXwYmeNvXu8qCLYErc7sjdXHBgXToo2DnSj86',
        'johndoe@example.com', '20001111', '190201-27314', 1);


-- INSERT INTO reservations(id, user_id, book_id, created_date, term_date, status, extension_count, updated_date)
-- VALUES (1, 2, 3, '2025-03-05 14:30:00', '2025-03-06 14:30:00', 1, 2, '2025-03-05 14:30:00'),
--        (2, 1, 4, '2025-03-07 14:30:00', '2025-03-08 14:30:00', 2, 3, '2025-03-07 14:30:00'),
--        (3, 4, 2, '2025-03-10 14:30:00', '2025-03-11 14:30:00', 1, 4, '2025-03-10 14:30:00'),
--        (4, 3, 1, '2025-03-12 14:30:00', '2025-03-13 14:30:00', 3, 5, '2025-03-12 14:30:00');
