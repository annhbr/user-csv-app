# User CSV app
Spring boot REST app with H2 database

## Endpoints:

### POST
* `upload file` [/api/users/upload](http://localhost:8080/api/v1/users/upload)

### GET
* `count users` [/api/users/count](http://localhost:8080/api/v1/users/count)
* `get users by last name` [/users/name/{lastName}](http://localhost:8080/api/v1//users/name/{lastName})
* `get users by id` [/api/users/{ids}](http://localhost:8080/api/v1/users/{ids})
* `get all users ordered by age` [/api/users/sorted](http://localhost:8080/api/v1/users/sorted)
* `get all users with pagination` [/api/users](http://localhost:8080/api/v1/users)

### DELETE
* `delete users by id` [/api/users/{ids}](http://localhost:8080/api/v1/users/{ids})

## Example valid CSV file

```
first_name,last_name,birth_date,phone_no
Jacek,Testowy,1988.11.11,600700800
Marian,Ziółko,1999.01.01,555666777
Jan,Kowalski,2000.02.04,666000111
piotr,kowalewski,1950.10.01,670540120
Zygmunt,       Stefanowicz,1991.06.01,
Ernest,Gąbka,1991.06.01,
Elzbieta,Zółw,1988.03.03,670540123
```
