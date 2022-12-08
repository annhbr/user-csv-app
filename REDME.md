# User CSV app
Spring boot REST app with H2 database

## Endpoints:

### POST
* `upload file` [/api/users/upload](http://localhost:8080/api/v1/users/upload)

### GET
* `count users` [/api/v1/users/count](http://localhost:8080/api/v1/users/count)
* `get users by last name` [api/v1/users/name/{lastName}](http://localhost:8080/api/v1/users/name/{lastName})
* `get users by id` [/api/v1/users/{ids}](http://localhost:8080/api/v1/users/{ids})
* `get all users ordered by age` [/api/v1/users/sorted](http://localhost:8080/api/v1/users/sorted)
* `get all users with pagination` [/api/v1/users](http://localhost:8080/api/v1/users)

### DELETE
* `delete users by id` [/api/v1/users/{ids}](http://localhost:8080/api/v1/users/{ids})

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

**Parameters**

|          Name | Required | Unique | Description                        |
| -------------:|:--------:|:------:|------------------------------------|
|    `first_name` | required |   no   | User name                          |
|    `last_name` | required |   no   | User last name                     |
|    `birth_date` | required |   no   | Date of birth in format yyyy.MM.dd |
|    `phone_no` | optional |  yes   | Nine digits phone number allowed   |
