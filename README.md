# codechallenge

#### Prerequisites

- Maven
- MySQL 
    - Create new schema name user
    - Input your database username and password to application.properties
    
#### Build

mvn clean install

#### Run

1) chmod +x start.sh
2) ./start.sh


#### Web (Port 8080)

`http://localhost:8080`

#### Restful endpoints (Port 8081)

##### List All Users
[GET] `http://localhost:8081/api/users`

##### Get One User
[GET] `http://localhost:8081/api/users/1`

##### Search User By Name
[GET] `http://localhost:8081/api/users/search?name=Leanne`

##### Update User
[PUT] `http://localhost:8081/api/users/1`

##### Delete User
[DELETE] `http://localhost:8081/api/users/1`
