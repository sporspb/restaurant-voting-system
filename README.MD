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
    - If it is before 11:00 we asume that he changed his mind.
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


### Restaurants

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get All              | {Base URL}/restaurant/all                           | Everyone       |
| GET    | Get                  | {Base URL}/restaurant/{restaurantID}                | Everyone       |
| POST   | Create               | {Base URL}/restaurant                               | Admin Only     |
| DELETE | Delete               | {Base URL}/restaurant/{restaurantID}                | Admin Only     |
| PUT    | Update               | {Base URL}/restaurant/{restaurantID}                | Admin Only     |

### Dishes

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get All              | {Base URL}/dish/all                                 | Admin Only     |
| GET    | Get                  | {Base URL}/dish/{restaurantID}                      | Admin Only     |
| POST   | Create               | {Base URL}/dish                                     | Admin Only     |
| DELETE | Delete               | {Base URL}/dish/{restaurantID}                      | Admin Only     |
| PUT    | Update               | {Base URL}/dish/{restaurantID}                      | Admin Only     |

### Menus

| Method | Description          | URL                                                 | Access         |
|--------|----------------------|-----------------------------------------------------|----------------|
| GET    | Get All              | {Base URL}/menu                                     | Everyone       |
| GET    | Get                  | {Base URL}/menu/{menuID}                            | Everyone       |
| POST   | Create               | {Base URL}/menu                                     | Admin Only     |
| DELETE | Delete               | {Base URL}/menu/{menuID}                            | Admin Only     |
| PUT    | Update               | {Base URL}/menu/{menuID}                            | Admin Only     |

### Votes

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
- Register a new user
curl -s -X POST -d '{"name": "newName","email": "newemail@ya.ru","password": "newPassword"}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/voting/rest/profile/register`

- Get own profile
`curl -s http://localhost:8080/voting/rest/profile --user user@yandex.ru:password`

- Update own profile
`curl -s -X PUT -d '{"name": "newName","email": "newemail2@ya.ru","password": "newPassword"}' -H 'Content-Type: application/json' http://localhost:8080/voting/rest/profile --user user@yandex.ru:password`

- Delete own profile
`curl -s -X DELETE http://localhost:8080/voting/rest/profile --user user@yandex.ru:password`

### Manage profiles by admin
- Create new profile
`curl -s -X POST -d '{"id": null,"name": "New User","email": "newemail2@gmail.com","password": "newPass","registered": "2019-09-12T12:00:00","enabled": true,"roles": ["ROLE_USER"]}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/voting/rest/admin/users --user admin@gmail.com:admin`

- Get all user profiles
`curl -s http://localhost:8080/voting/rest/admin/users --user admin@gmail.com:admin`

- Get user profile by id
`curl -s http://localhost:8080/voting/rest/admin/users/100000 --user admin@gmail.com:admin`

- Get user profile by email
`curl -s http://localhost:8080/voting/rest/admin/users/by?email=user@yandex.ru --user admin@gmail.com:admin`

- Update user profile by id
`curl -s -X PUT -d '{"id": 100001,"name": "UpdatingName","email": "updatingemail@gmail.com", "password": "updatingPassword"}' -H 'Content-Type: application/json' http://localhost:8080/voting/rest/admin/users/100001 --user admin@gmail.com:admin`

- Delete user profile by id
`curl -s -X DELETE http://localhost:8080/voting/rest/admin/users/100001 --user admin@gmail.com:admin`
