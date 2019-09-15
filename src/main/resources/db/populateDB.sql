DELETE
FROM votes;
DELETE
FROM dishes;
DELETE
FROM restaurants;
DELETE
FROM user_roles;
DELETE
FROM users;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User1', 'user1@yandex.ru', '{noop}user');

INSERT INTO user_roles (user_id, role)
VALUES (100000, 'ROLE_ADMIN'),
       (100000, 'ROLE_USER'),
       (100001, 'ROLE_USER');

INSERT INTO restaurants (name)
-- ID's: 100002, 100003, 100004
VALUES ('Restaurant1'),
       ('Restaurant2'),
       ('Restaurant3');

INSERT INTO dishes (restaurant_id, name, price, date)
-- ID's: 100005, 100006, 100007, 100008, 100009, 100010, 100011
VALUES (100002, 'Lasagna', 155, '2019-08-19'),
       (100002, 'Roast beef', 190, '2019-08-19'),
       (100003, 'Salad', 70, '2019-08-19'),
       (100003, 'Cake', 80, '2019-08-19'),
       (100004, 'Risotto', 90, '2019-08-18'),
       (100004, 'Soup', 50, '2019-08-18'),
       (100004, 'Fish', 115, '2019-08-18');

INSERT INTO votes (user_id, restaurant_id, date)
-- ID's: 100012, 100013, 100014, 100015
VALUES (100001, 100002, '2019-08-18'),
       (100000, 100002, '2019-08-18'),
       (100001, 100002, '2019-08-19'),
       (100000, 100003, '2019-08-19');
