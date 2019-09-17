DELETE
FROM votes;
DELETE
FROM menus;
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
       ('User', 'user@yandex.ru', '{noop}password');

INSERT INTO user_roles (user_id, role)
VALUES (100000, 'ROLE_ADMIN'),
       (100000, 'ROLE_USER'),
       (100001, 'ROLE_USER');

INSERT INTO restaurants (name)
-- ID's: 100002, 100003, 100004
VALUES ('Restaurant1'),
       ('Restaurant2'),
       ('Restaurant3');

INSERT INTO dishes (name, price)
-- ID's: 100005, 100006, 100007, 100008, 100009, 100010, 100011
VALUES ('Lasagna', 155),
       ('Roast beef', 190),
       ('Salad', 70),
       ('Cake', 80),
       ('Risotto', 90),
       ('Soup', 50),
       ('Fish', 115);

INSERT INTO menus(dish_id, restaurant_id, date)
-- ID's: 100012, 100013, 100014, 100015, 100016, 100017, 100018
VALUES (100005, 100002, '2019-08-19'),
       (100006, 100002, '2018-08-19'),

       (100007, 100003, '2019-08-19'),
       (100008, 100003, '2019-08-19'),

       (100009, 100004, '2019-08-20'),
       (100010, 100004, '2019-08-20'),
       (100011, 100004, '2019-08-20');

INSERT INTO votes (user_id, restaurant_id, date)
-- ID's: 100019, 100020, 100021, 100022
VALUES (100001, 100002, '2019-08-19'),
       (100000, 100002, '2019-08-19'),
       (100001, 100002, '2019-08-20'),
       (100000, 100003, '2019-08-20');
