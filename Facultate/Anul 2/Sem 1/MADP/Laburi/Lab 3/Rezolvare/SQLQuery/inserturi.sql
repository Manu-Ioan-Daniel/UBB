
INSERT INTO flocks (flock_name) VALUES ('Flying Masters');
INSERT INTO flocks (flock_name) VALUES ('Swimming Squad');


INSERT INTO users (username, email, userPassword) VALUES ('donald', 'donald@example.com', '123');
INSERT INTO users (username, email, userPassword) VALUES ('daisy', 'daisy@example.com', '123');
INSERT INTO users (username, email, userPassword) VALUES ('scrooge', 'scrooge@example.com', '123');
INSERT INTO users (username, email, userPassword) VALUES ('huey', 'huey@example.com', '123');


INSERT INTO ducks (user_id, duckType, speed, rezistance, flock_id) VALUES (1, 'FLYING', 10.0, 8.0, 1);
INSERT INTO ducks (user_id, duckType, speed, rezistance, flock_id) VALUES (2, 'FLYING', 9.5, 7.5, 1);
INSERT INTO ducks (user_id, duckType, speed, rezistance, flock_id) VALUES (3, 'SWIMMING', 8.0, 9.0, 2);
INSERT INTO ducks (user_id, duckType, speed, rezistance, flock_id) VALUES (4, 'SWIMMING', 7.5, 8.5, 2);


INSERT INTO user_friends (user_id, friend_id) VALUES (1, 2);
INSERT INTO user_friends (user_id, friend_id) VALUES (2, 1);
INSERT INTO user_friends (user_id, friend_id) VALUES (3, 4);
INSERT INTO user_friends (user_id, friend_id) VALUES (4, 3);
