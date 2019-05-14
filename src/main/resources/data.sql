insert into role (id, name) values
  ('1', 'ROLE_ADMIN'),
  ('2', 'ROLE_USER');

insert into user (id, username, password) values
  ('1', 'admin', 'test'),
  ('2', 'user', 'test');

insert into users_roles (user_id, role_id) values
  ('1', '1'),
  ('1', '2'),
  ('2', '2');