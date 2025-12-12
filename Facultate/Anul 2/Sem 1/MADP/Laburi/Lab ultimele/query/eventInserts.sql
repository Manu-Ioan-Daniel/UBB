-- Additional SWIMMING Ducks to have 10 total
INSERT INTO users (userId, username, email, userPassword) VALUES
(9, 'mickey', 'mickey@example.com', '123'),
(10, 'minnie', 'minnie@example.com', '123'),
(11, 'donald2', 'donald2@example.com', '123'),
(12, 'daisy2', 'daisy2@example.com', '123'),
(13, 'goofy', 'goofy@example.com', '123'),
(14, 'pluto', 'pluto@example.com', '123');

INSERT INTO ducks (userId, duckType, duckSpeed, duckRezistance) VALUES
(9, 'SWIMMING', 8.2, 7.8),
(10, 'SWIMMING', 7.9, 7.9),
(11, 'SWIMMING', 8.5, 8.0),
(12, 'SWIMMING', 7.8, 8.1),
(13, 'SWIMMING', 8.1, 8.2),
(14, 'SWIMMING', 7.6, 8.3);

INSERT INTO events (eventId, eventType) VALUES
(1, 'RaceEvent'),
(2, 'RaceEvent'),
(3, 'RaceEvent');

INSERT INTO raceEvents (eventId, M) VALUES
(1, 6),
(2, 4),
(3, 2);

INSERT INTO eventMembers (eventId, userId) VALUES
(1, 9),
(1, 10),
(1, 11),
(1,12),
(1,13),
(1,14);

INSERT INTO eventMembers (eventId, userId) VALUES
(2, 9),
(2, 11),
(2, 13);

INSERT INTO eventMembers (eventId, userId) VALUES
(3,10),
(3,14);
