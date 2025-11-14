-- Flocks
INSERT INTO flocks (flock_name) VALUES ('Flying Masters');
INSERT INTO flocks (flock_name) VALUES ('Swimming Squad');

-- Users (acum trebuie să specificăm userId)
INSERT INTO users (userId, username, email, userPassword) 
VALUES (1, 'donald', 'donald@example.com', '123');
INSERT INTO users (userId, username, email, userPassword) 
VALUES (2, 'daisy', 'daisy@example.com', '123');
INSERT INTO users (userId, username, email, userPassword) 
VALUES (3, 'scrooge', 'scrooge@example.com', '123');
INSERT INTO users (userId, username, email, userPassword) 
VALUES (4, 'huey', 'huey@example.com', '123');

-- Ducks
INSERT INTO ducks (user_id, duckType, duckSpeed, duckRezistance, flock_id) 
VALUES (1, 'FLYING', 10.0, 8.0, 1);
INSERT INTO ducks (user_id, duckType, duckSpeed, duckRezistance, flock_id) 
VALUES (2, 'FLYING', 9.5, 7.5, 1);
INSERT INTO ducks (user_id, duckType, duckSpeed, duckRezistance, flock_id) 
VALUES (3, 'SWIMMING', 8.0, 9.0, 2);
INSERT INTO ducks (user_id, duckType, duckSpeed, duckRezistance, flock_id) 
VALUES (4, 'SWIMMING', 7.5, 8.5, 2);

-- Users persoane
INSERT INTO users (userId, username, email, userPassword)
VALUES 
(5, 'alexandra', 'alexandra@example.com', '123'),
(6, 'vlad', 'vlad@example.com', '123'),
(7, 'cristina', 'cristina@example.com', '123'),
(8, 'bogdan', 'bogdan@example.com', '123');

-- Persons
INSERT INTO persons (user_id, personName, personSurname, personOccupation, personDateOfBirth, personEmpathyScore)
VALUES
(5, 'Alexandra', 'Mihalache', 'Medic', '1990-09-17', 92),
(6, 'Vlad', 'Petrescu', 'Programator', '1994-03-08', 75),
(7, 'Cristina', 'Lungu', 'Designer', '1998-11-29', 88),
(8, 'Bogdan', 'Radu', 'Profesor', '1985-01-23', 65);

-- User friends
INSERT INTO user_friends (user_id, friend_id) 
VALUES (1, 2),
       (2, 1),
       (3, 4),
       (4, 3),
       (4, 5),
       (5, 4),
       (6, 7),
       (7, 6);
