CREATE TABLE users (
    userId BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    userPassword VARCHAR(100) NOT NULL
);

CREATE TABLE user_friends (
    user_id BIGINT REFERENCES users(userId) ON DELETE CASCADE,
    friend_id BIGINT REFERENCES users(userId) ON DELETE CASCADE,
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE flocks (
    flockId BIGSERIAL PRIMARY KEY,
    flock_name VARCHAR(100) NOT NULL
);

CREATE TABLE ducks (
    user_id BIGINT PRIMARY KEY REFERENCES users(userId) ON DELETE CASCADE,
    duckType VARCHAR(100) NOT NULL,
    speed DOUBLE PRECISION NOT NULL,
    rezistance DOUBLE PRECISION NOT NULL,
    flock_id BIGINT REFERENCES flocks(flockId) ON DELETE SET NULL
);
