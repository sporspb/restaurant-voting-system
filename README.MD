# Restaurant Voting System

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

-------
## REST API

Building project: 
1) mvn package
2) mvn cargo:run

Package deployed into app context "voting"

Base URL: `localhost:8080/voting/rest/`

### Existing users
 After initialization, you can log in under one of the existing users :  

1) **Username** - `admin@gmail.com`	 **Password** - `admin` **Role** - ADMIN

2) **Username** - `user@yandex.ru`	 **Password** - `password`	**Role** - USER

---

### API documentation


### Manage profiles by admin


| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | get all profiles     | {Base URL}/admin/users                              | Admin          |
| GET    | get profile          | {Base URL}/admin/users/{userID}                     | Admin          |
| GET    | get profile by email | {Base URL}/admin/users/by?email={userEMAIL}         | Admin          |
| POST   | Create Profile       | {Base URL}/admin/users                              | Admin          |
| DELETE | Delete Profile       | {Base URL}/admin/users/{userID}                     | Admin          |
| PUT    | Update Profile       | {Base URL}/admin/users/{userID}                     | Admin          |


### Manage own profile by user

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get Profile          | {Base URL}/profile                                  | Authorized     |
| PUT    | Update Profile       | {Base URL}/profile                                  | Authorized     |
| DELETE | Delete Profile       | {Base URL}/profile                                  | Authorized     |
| POST   | Register Profile     | {Base URL}/profile/register                         | Not Authorized |


### Manage restaurants

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get All              | {Base URL}/restaurants                              | Everyone       |
| GET    | Get                  | {Base URL}/restaurants/{restaurantID}                | Everyone       |
| POST   | Create               | {Base URL}/restaurants                               | Admin Only     |
| DELETE | Delete               | {Base URL}/restaurants/{restaurantID}                | Admin Only     |
| PUT    | Update               | {Base URL}/restaurants/{restaurantID}                | Admin Only     |

### Manage dishes

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get All              | {Base URL}/dish                                     | Admin Only     |
| GET    | Get                  | {Base URL}/dish/{dishID}                            | Admin Only     |
| POST   | Create               | {Base URL}/dish                                     | Admin Only     |
| DELETE | Delete               | {Base URL}/dish/{dishID}                            | Admin Only     |
| PUT    | Update               | {Base URL}/dish/{dishID}                            | Admin Only     |

### Manage menus

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get All              | {Base URL}/menu                                     | Everyone       |
| GET    | Get                  | {Base URL}/menu/{menuID}                            | Everyone       |
| GET    | Get by date          | {Base URL}/menu?date={date}                         | Everyone       |
| POST   | Create               | {Base URL}/menu                                     | Admin Only     |
| DELETE | Delete               | {Base URL}/menu/{menuID}                            | Admin Only     |
| PUT    | Update               | {Base URL}/menu/{menuID}                            | Admin Only     |

### Manage votes

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get All              | {Base URL}/vote                                     | Authorized     |
| GET    | Get                  | {Base URL}/vote/{voteID}                            | Authorized     |
| POST   | Create               | {Base URL}/vote                                     | Authorized     |
| DELETE | Delete               | {Base URL}/vote/{voteID}                            | Authorized     |
| PUT    | Update               | {Base URL}/vote/{voteID}                            | Authorized     |

---

## CURL SAMPLES

### User manage own profile
- Register a new user:
`curl -s -X POST -d '{"name": "newName","email": "newemail@ya.ru","password": "newPassword"}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/voting/rest/profile/register`

- Get own profile:
`curl -s http://localhost:8080/voting/rest/profile --user user@yandex.ru:password`

- Update own profile:
`curl -s -X PUT -d '{"name": "newName","email": "newemail2@ya.ru","password": "newPassword"}' -H 'Content-Type: application/json' http://localhost:8080/voting/rest/profile --user user@yandex.ru:password`

- Delete own profile:
`curl -s -X DELETE http://localhost:8080/voting/rest/profile --user user@yandex.ru:password`

### Manage profiles by admin:
- Create new profile
`curl -s -X POST -d '{"id": null,"name": "New User","email": "newemail2@gmail.com","password": "newPass","registered": "2019-09-12T12:00:00","enabled": true,"roles": ["ROLE_USER"]}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/voting/rest/admin/users --user admin@gmail.com:admin`

- Get all user profiles:
`curl -s http://localhost:8080/voting/rest/admin/users --user admin@gmail.com:admin`

- Get user profile by id:
`curl -s http://localhost:8080/voting/rest/admin/users/100000 --user admin@gmail.com:admin`

- Get user profile by email:
`curl -s http://localhost:8080/voting/rest/admin/users/by?email=user@yandex.ru --user admin@gmail.com:admin`

- Update user profile by id:
`curl -s -X PUT -d '{"id": 100001,"name": "UpdatingName","email": "updatingemail@gmail.com", "password": "updatingPassword"}' -H 'Content-Type: application/json' http://localhost:8080/voting/rest/admin/users/100001 --user admin@gmail.com:admin`

- Delete user profile by id:
`curl -s -X DELETE http://localhost:8080/voting/rest/admin/users/100001 --user admin@gmail.com:admin`

### Manage dishes by admin

- Create a new dish:
`curl -s -X POST -d '{"name":"New dish", "price":"1000"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/dish --user admin@gmail.com:admin`

- Get all dishes:
`curl -s http://localhost:8080/voting/rest/dish --user admin@gmail.com:admin`

- Get dish by id:
`curl -s http://localhost:8080/voting/rest/dish/100005 --user admin@gmail.com:admin`

- Update dish by id:
`curl -s -X PUT -d '{"id":"100005", "name":"Updated dish", "price":"111"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/dish/100005 --user admin@gmail.com:admin`

- Delete dish by id:
`curl -s -X DELETE http://localhost:8080/voting/rest/dish/100005 --user admin@gmail.com:admin`

### Manage restaurants by admin
- Create new restaurant:
`curl -s -X POST -d '{"name":"New restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/restaurants --user admin@gmail.com:admin`

- Update restaurant by id:
`curl -s -X PUT -d '{"id":"100002", "name":"Updated restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/restaurants/100002 --user admin@gmail.com:admin`

- Delete restaurant by id:
`curl -s -X DELETE http://localhost:8080/voting/rest/restaurants/100002 --user admin@gmail.com:admin`

### Manage restaurants by User
- Get restaurant by id:
`curl -s http://localhost:8080/voting/rest/restaurants/100003 --user user@yandex.ru:password`

- Get all restaurants:
`curl -s http://localhost:8080/voting/rest/restaurants --user user@yandex.ru:password`

### Menu
- Get all menus:
`curl -s http://localhost:8080/voting/rest/menu --user user@yandex.ru:password`

- Get menu by id:
`curl -s http://localhost:8080/voting/rest/menu/100017 --user user@yandex.ru:password`

- Get menu by date
`curl -s http://localhost:8080/voting/rest/menu?date=2019-08-19 --user user@yandex.ru:password`
- Create new menu:
`curl -s -X POST -d '{"date":"2018-12-31", "restaurantId":"100003", "dishId":"100005"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/menu --user admin@gmail.com:admin`

- Update menu by id:
`curl -s -X PUT -d '{"id":"100015", "date":"2019-01-01", "restaurantId":"100003", "dishId":"100010"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/menu/100015 --user admin@gmail.com:admin`

- Delete menu by id:
`curl -s -X DELETE http://localhost:8080/voting/rest/menu/100016 --user admin@gmail.com:admin`

### Vote
- User vote for restaurant:
`curl -s -X POST http://localhost:8080/voting/rest/vote/100003 --user user@yandex.ru:password`

- Get vote by id:
`curl -s http://localhost:8080/voting/rest/admin/vote/100020 --user admin@gmail.com:admin`

- Get all votes:
`curl -s http://localhost:8080/voting/rest/admin/vote --user admin@gmail.com:admin`

- Delete vote by id:
`curl -s -X DELETE http://localhost:8080/voting/rest/admin/vote/100020 --user admin@gmail.com:admin`