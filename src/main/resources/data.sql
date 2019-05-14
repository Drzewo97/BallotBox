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

insert into choice(id, name) values
  ('1', 'choice1'),
  ('2', 'choice2'),
  ('3', 'choice3'),
  ('4', 'choice4'),
  ('5', 'choice5'),
  ('6', 'choice6');

insert into poll (id, choices_count, description, name, open_from, open_until, voting_mode, creator_id) values
  ('1', '1', 'descp1', 'poll 1', '2019-06-18 08:00:00', '2019-06-19 08:00:00', 'AT_MOST', '1'),
  ('2', '2', 'descp2', 'poll 2', '2019-06-20 08:00:00', '2019-06-21 08:00:00', 'EXACTLY', '1');

insert into user_polls_created (user_id, polls_created_id) values
  ('1', '1'),
  ('1', '2');

insert into polls_choices (poll_id, choice_id) values
  ('1', '1'),
  ('1', '2'),
  ('1', '3'),
  ('1', '4'),
  ('1', '5'),
  ('1', '6'),
  ('2', '1'),
  ('2', '2'),
  ('2', '3'),
  ('2', '4'),
  ('2', '5'),
  ('2', '6');
