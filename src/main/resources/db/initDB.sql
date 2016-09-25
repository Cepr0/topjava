drop table if exists meals;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE users
(
  id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name       VARCHAR NOT NULL,
  email      VARCHAR NOT NULL,
  password   VARCHAR NOT NULL,
  registered TIMESTAMP DEFAULT now(),
  enabled    BOOL DEFAULT TRUE,
  calories_per_day INTEGER DEFAULT 2000 NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

create table meals (
  id integer primary key default nextval('global_seq'),
  date_time timestamp default now(),
  description varchar not null,
  calories integer not null,
  user_id integer not null,
  foreign key (user_id) references users (id) on delete cascade
);
-- create index date_time_idx on meals (date_time);
create unique index unique_date_time_user_idx on meals (date_time, user_id)
-- create index user_id_idx on meals (user_id);




