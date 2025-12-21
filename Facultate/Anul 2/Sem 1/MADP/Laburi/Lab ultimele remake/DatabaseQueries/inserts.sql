INSERT INTO users (username,password,email)
SELECT
	'username' || i,
	'password' || i,
	'email' || i
FROM generate_series(1,10) AS i;