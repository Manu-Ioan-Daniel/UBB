USE ParcDistractiiSeminar2
GO
CREATE PROCEDURE AdaugaSectiune @nume VARCHAR(100),@descriere VARCHAR(100)
AS
BEGIN
	INSERT INTO Sectiuni (numeSectiune,descriereSectiune) VALUES (@nume,@descriere);
END
EXEC AdaugaSectiune 'Ok','Ok';
GO
CREATE PROCEDURE ModificaVizitator @id INT,@adresa VARCHAR(100)
AS
BEGIN
	UPDATE Vizitatori
	SET email=@adresa
	WHERE vizitatorID=@id;
END
EXEC ModificaVizitator 1,'suspect@gmail.com';
GO
CREATE OR ALTER PROCEDURE NumarNote @nrNote INT OUTPUT
AS
BEGIN
	SELECT @nrNote = COUNT(*) FROM Vizitatori V
	INNER JOIN Note N ON N.vizitatorID=V.vizitatorID
	INNER JOIN Atractii A ON A.atractieID=N.atractieID
END
DECLARE @nr_note AS INT
SET @nr_note=0
EXEC NumarNote @nrNote=@nr_note OUTPUT;
PRINT @nr_note
GO
SELECT COUNT(*) FROM Vizitatori V
INNER JOIN Note N ON N.vizitatorID=V.vizitatorID
INNER JOIN Atractii A ON A.atractieID=N.atractieID


