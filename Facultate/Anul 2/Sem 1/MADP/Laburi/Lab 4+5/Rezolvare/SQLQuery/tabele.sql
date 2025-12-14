CREATE TABLE users (
    userId BIGINT PRIMARY KEY IDENTITY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    userPassword VARCHAR(100) NOT NULL
);

CREATE TABLE userFriends (
    userId BIGINT REFERENCES users(userId) ON DELETE CASCADE,
    friendId BIGINT REFERENCES users(userId) ON DELETE CASCADE,
    PRIMARY KEY (userId, friendId)
);

CREATE TABLE flocks (
    flockId BIGINT PRIMARY KEY IDENTITY,
    flockName VARCHAR(100) NOT NULL
);

CREATE TABLE ducks (
    userId BIGINT PRIMARY KEY REFERENCES users(userId) ON DELETE CASCADE,
    duckType VARCHAR(100) NOT NULL,
    duckSpeed DOUBLE PRECISION NOT NULL,
    duckRezistance DOUBLE PRECISION NOT NULL
);

CREATE TABLE persons (
    userId BIGINT PRIMARY KEY REFERENCES users(userId) ON DELETE CASCADE,
    personName VARCHAR(100) NOT NULL,
    personSurname VARCHAR(100) NOT NULL,
    personOccupation VARCHAR(100) NOT NULL,
    personDateOfBirth VARCHAR(100) NOT NULL,
    personEmpathyScore BIGINT NOT NULL
);

CREATE TABLE flockMembers (
    userId BIGINT REFERENCES users(userId) ON DELETE CASCADE,
    flockId BIGINT REFERENCES flocks(flockId) ON DELETE CASCADE
);
CREATE TABLE events(
	eventId BIGINT PRIMARY KEY IDENTITY,
	eventName VARCHAR(100) NOT NULL
	eventType VARCHAR(100) NOT NULL
);
CREATE TABLE eventMembers(
	eventId BIGINT REFERENCES events(eventId) ON DELETE CASCADE,
	userId BIGINT  REFERENCES users(userId) ON DELETE CASCADE,
	PRIMARY KEY (eventId,userId)
);
CREATE TABLE raceEvents(
	eventId BIGINT PRIMARY KEY REFERENCES events(eventId) ON DELETE CASCADE,
	M BIGINT NOT NULL
);
CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY,
    from_id BIGINT NOT NULL REFERENCES users(userid),
    message TEXT NOT NULL,
    date TIMESTAMP NOT NULL,
    reply_to BIGINT REFERENCES messages(id)
);

CREATE TABLE message_recipients (
    message_id BIGINT NOT NULL REFERENCES messages(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(userid),
    PRIMARY KEY(message_id, user_id)
);

