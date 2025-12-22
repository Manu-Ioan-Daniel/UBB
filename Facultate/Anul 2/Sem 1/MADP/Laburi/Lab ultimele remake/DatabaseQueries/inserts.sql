
INSERT INTO users (username,password,email,type)
SELECT
	'username' || i,
	'password' || i,
	'email' || i,
	'duck'
FROM generate_series(1,5) AS i;

INSERT INTO users (username,password,email,type)
SELECT
	'username' || i,
	'password' || i,
	'email' || i || '@gmail.com'
	'person'
FROM generate_series(6,10) AS i;

INSERT INTO ducks (id,type,speed,resistance)
SELECT
	i,
	'FLYING',
	i,
	i
FROM generate_series(1,3) AS i;

INSERT INTO ducks (id,type,speed,resistance)
SELECT
	i,
	'SWIMMING',
	i,
	i
FROM generate_series(4,5) AS i;

INSERT INTO people (id,name,surname,date_of_birth,occupation,empathy_level)
SELECT
	i,
	'name' || i,
	'surname' || i,
	DATE '1990-01-01' + i,
	'occupation' || i,
	i%10
FROM generate_series(6,10) AS i;

