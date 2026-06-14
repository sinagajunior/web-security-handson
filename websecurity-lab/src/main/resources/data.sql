INSERT INTO users (id, username, email, password, role) VALUES
(1, 'admin', 'admin@lab.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTyGgGa9Gy', 'ADMIN'),
(2, 'alice', 'alice@lab.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTyGgGa9Gy', 'USER'),
(3, 'bob',   'bob@lab.com',   '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTyGgGa9Gy', 'USER');
-- All passwords are BCrypt of "password123"