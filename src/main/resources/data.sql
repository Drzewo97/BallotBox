insert into role (id, name) values
  ('1', 'ROLE_ADMIN'),
  ('2', 'ROLE_USER'),
  ('3', 'ROLE_WARDADMIN'),
  ('4', 'ROLE_COMMITTEEADMIN');

-- insert only if table is empty
INSERT INTO user (id, username, password, active)
SELECT '1', 'admin', 'ef51da8f5c6e8f525aacbcf1a09653873c59cac99b09c94e', '1' FROM DUAL
WHERE NOT EXISTS (SELECT * FROM user);
