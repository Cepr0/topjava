DELETE FROM users;
DELETE FROM user_roles;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

-- User meals
insert into meals (date_time, description, calories, user_id) values
  ('2016-09-23 10:00', 'Завтрак ' || 100000, 500, 100000);
insert into meals (date_time, description, calories, user_id) values
  ('2016-09-23 14:00', 'Обед ' || 100000, 1000, 100000);
insert into meals (date_time, description, calories, user_id) values
  ('2016-09-23 19:00', 'Ужин ' || 100000, 500, 100000);

insert into meals (date_time, description, calories, user_id) values
  ('2016-09-24 10:00', 'Завтрак ' || 100000, 500, 100000);
insert into meals (date_time, description, calories, user_id) values
  ('2016-09-24 14:00', 'Обед ' || 100000, 1000, 100000);
insert into meals (date_time, description, calories, user_id) values
  ('2016-09-24 19:00', 'Ужин ' || 100000, 600, 100000);

-- Admin meals
insert into meals (date_time, description, calories, user_id) values
  ('2016-09-23 10:00', 'Завтрак ' || 100001, 500, 100001);
insert into meals (date_time, description, calories, user_id) values
  ('2016-09-23 14:00', 'Обед ' || 100001, 1000, 100001);
insert into meals (date_time, description, calories, user_id) values
  ('2016-09-23 19:00', 'Ужин ' || 100001, 600, 100001);

insert into meals (date_time, description, calories, user_id) values
  ('2016-09-24 10:00', 'Завтрак ' || 100001, 500, 100001);
insert into meals (date_time, description, calories, user_id) values
  ('2016-09-24 14:00', 'Обед ' || 100001, 1000, 100001);
insert into meals (date_time, description, calories, user_id) values
  ('2016-09-24 19:00', 'Ужин ' || 100001, 500, 100001);

