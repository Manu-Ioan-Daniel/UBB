-- Flocks
INSERT INTO flocks (flockName) 
VALUES 
	('Flying Masters'),
	('Swimming Squad');

-- Ducks
INSERT INTO users (username, email, userPassword)
VALUES 
	('donald', 'donald@example.com', '123'),
	('daisy', 'daisy@example.com', '123'),
	('scrooge', 'scrooge@example.com', '123'),
	('huey', 'huey@example.com', '123');

INSERT INTO ducks (userId, duckType, duckSpeed, duckRezistance)
VALUES
	(1, 'FLYING', 10.0, 8.0),
	(2, 'FLYING', 9.5, 7.5),
	(3, 'SWIMMING', 8.0, 9.0),
	(4, 'SWIMMING', 7.5, 8.5);

-- Persoane
INSERT INTO users (username, email, userPassword)
VALUES
	('alexandra', 'alexandra@example.com', '123'),
	('vlad', 'vlad@example.com', '123'),
	('cristina', 'cristina@example.com', '123'),
	('bogdan', 'bogdan@example.com', '123');


INSERT INTO persons (userId, personName, personSurname, personOccupation, personDateOfBirth, personEmpathyScore)
VALUES
	(5, 'Alexandra', 'Mihalache', 'Medic', '1990-09-17', 92),
	(6, 'Vlad', 'Petrescu', 'Programator', '1994-03-08', 75),
	(7, 'Cristina', 'Lungu', 'Designer', '1998-11-29', 88),
	(8, 'Bogdan', 'Radu', 'Profesor', '1985-01-23', 65);

-- User friends
INSERT INTO userfriends (userId, friendId)
VALUES
	(1, 2),
	(2, 1),
	(3, 4),
	(4, 3),
	(4, 5),
	(5, 4),
	(6, 7),
	(7, 6);

-- Flock members
INSERT INTO flockmembers (userId, flockId)
VALUES
	(1, 1),
	(2, 1),
	(3, 2),
	(4, 2);
