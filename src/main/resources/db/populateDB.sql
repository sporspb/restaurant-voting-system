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

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 1000;

INSERT INTO users (name, email, password)
VALUES ('Jason', 'jason@gmail.com', '{noop}admin'),
       ('Anna', 'anuta@gmail.com', '{noop}user');

INSERT INTO user_roles (user_id, role)
VALUES (1000, 'ROLE_ADMIN'),
       (1001, 'ROLE_USER');

INSERT INTO restaurants (name)
VALUES ('Restaurant1'),
       ('Restaurant2'),
       ('Restaurant3');

INSERT INTO dishes (restaurant_id, name, price, date)
VALUES (1002, 'Lasagna', 155, '2019-08-19'),
       (1003, 'Salad', 70, '2019-08-19'),
       (1003, 'Bred', 10, '2019-08-19'),
       (1004, 'Risotto', 90, '2019-08-18'),
       (1004, 'Soup', 50, '2019-08-18'),
       (1004, 'Fish', 115, '2019-08-18');

INSERT INTO votes (user_id, restaurant_id, date)
VALUES (1000, 1003, '2019-08-11'),
       (1000, 1002, '2019-08-12'),
       (1001, 1002, '2019-08-11');
