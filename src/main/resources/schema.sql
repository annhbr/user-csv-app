CREATE TABLE user_details(
id INTEGER GENERATED BY DEFAULT AS IDENTITY,
first_name      VARCHAR(255) NOT NULL,
last_name       VARCHAR(255) NOT NULL,
birth_date      VARCHAR(255) NOT NULL,
phone_number    VARCHAR(255),
PRIMARY KEY (id)
);