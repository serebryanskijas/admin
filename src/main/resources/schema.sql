

CREATE TABLE role(id INTEGER NOT NULL AUTO_INCREMENT,
                  name TEXT NOT NULL);

CREATE TABLE users(id INTEGER NOT NULL AUTO_INCREMENT,
                  username TEXT NOT NULL,
                  password TEXT NOT NULL,
                  email TEXT NOT NULL,
                  first_name TEXT NOT NULL,
                  last_name TEXT NOT NULL,
                  enabled boolean default false
);

CREATE TABLE users_roles(
                            user_id integer not null,
                            role_id integer NOT NULL
);