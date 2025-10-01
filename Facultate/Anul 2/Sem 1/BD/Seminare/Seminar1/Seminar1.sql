--comentariu
/* comentariu pe mai multe linii
..
..
*/
--crearea unei baze de date
CREATE DATABASE SEMINAR12242025
-- se pune GO intre create database si use daca sunt in acelasi batch de executie
--conectarea la baza de date
USE SEMINAR12242025

CREATE TABLE Utilizatori
	(cod_u INT PRIMARY KEY IDENTITY,
	nume_u VARCHAR(100) NOT NULL UNIQUE,
	parola VARCHAR(100),
	email VARCHAR(100),
	);

CREATE TABLE Companii
(cod_c INT PRIMARY KEY IDENTITY(1,1),
nume_c NVARCHAR(100),
tara NVARCHAR(100),
);

CREATE TABLE Filme
(cod_f INT PRIMARY KEY IDENTITY,
titlu NVARCHAR(200),
durata TIME,
limba NVARCHAR(100),
cod_c INT FOREIGN KEY REFERENCES Companii(cod_c) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Note
(cod_u INT,
cod_f INT,
nota INT,
CONSTRAINT fk_UtilizatoriNote FOREIGN KEY(cod_u) REFERENCES Utilizatori(cod_u),
CONSTRAINT fk_FilmeNote FOREIGN KEY (cod_f) REFERENCES Filme(cod_f),
CONSTRAINT pk_note PRIMARY KEY (cod_u,cod_f)
);

ALTER TABLE Utilizatori
ADD CONSTRAINT uq_email UNIQUE(email)

ALTER TABLE Note
ADD CONSTRAINT ck_nota CHECK(nota>=1 AND nota<=10)

ALTER TABLE Note
ADD data_si_ora_adaugarii DATETIME;

ALTER TABLE Note
ADD CONSTRAINT df_data_si_ora_adaugarii DEFAULT GETDATE() FOR data_si_ora_adaugarii;

ALTER TABLE Filme
ALTER COLUMN titlu NVARCHAR(220);

