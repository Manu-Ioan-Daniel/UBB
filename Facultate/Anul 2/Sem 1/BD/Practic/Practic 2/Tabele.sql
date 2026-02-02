CREATE TABLE Tip_Restaurante(
	id INT PRIMARY KEY IDENTITY(1,1),
	nume VARCHAR(100) NOT NULL,
	descriere VARCHAR(100) NOT NULL
);

CREATE TABLE Orase(
	id INT PRIMARY KEY IDENTITY(1,1),
	nume VARCHAR(100) NOT NULL
);

CREATE TABLE Restaurante(
	id INT PRIMARY KEY IDENTITY(1,1),
	nume VARCHAR(100) NOT NULL,
	adresa VARCHAR(100) NOT NULL,
	nr_telefon VARCHAR(100) NOT NULL,
	oras_id INT FOREIGN KEY REFERENCES Orase(id),
	tip_restaurant_id INT FOREIGN KEY REFERENCES Tip_Restaurante(id)
);

CREATE TABLE Utilizatori(
	id INT PRIMARY KEY IDENTITY(1,1),
	nume VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL,
	parola VARCHAR(100) NOT NULL
);

CREATE TABLE Note_Restaurant(
	utilizator_id INT FOREIGN KEY REFERENCES Utilizatori(id),
	restaurant_id INT FOREIGN KEY REFERENCES Restaurante(id),
	PRIMARY KEY (utilizator_id,restaurant_id),
	nota REAL NOT NULL
);


