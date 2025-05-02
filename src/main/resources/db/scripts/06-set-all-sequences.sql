SELECT setval('authors_id_seq', (SELECT MAX(id) FROM authors));
SELECT setval('books_id_seq', (SELECT MAX(id) FROM books));
SELECT setval('reservations_id_seq', (SELECT MAX(id) FROM reservations));
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));