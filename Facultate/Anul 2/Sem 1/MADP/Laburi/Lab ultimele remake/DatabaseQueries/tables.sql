CREATE TABLE users(
	id SERIAL PRIMARY KEY,
	username VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(512) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	type VARCHAR(50) NOT NULL,
	CHECK (type IN ('duck', 'person'))
);

CREATE TABLE ducks(
	id  BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
	type VARCHAR(100),
	CHECK (type IN ('SWIMMING','FLYING')),
	speed DOUBLE PRECISION,
	resistance DOUBLE PRECISION
);

CREATE TABLE people(
	id BIGINT PRIMARY KEY REFERENCES users(id)
	ON DELETE CASCADE,
	name VARCHAR(100) NOT NULL,
	surname VARCHAR(100) NOT NULL,
	date_of_birth DATE,
	occupation VARCHAR(100),
	empathy_level BIGINT CHECK (empathy_level >=0 AND empathy_level <=10)
	
);


