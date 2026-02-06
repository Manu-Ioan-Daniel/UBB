CREATE PROCEDURE asociaza_autor
	@Nume VARCHAR(100),
	@Prenume VARCHAR(100),
	@ID_carte INT
AS
BEGIN
	DECLARE @id_autor INT;
	
	IF NOT EXISTS(SELECT 1 FROM Autori WHERE nume = @Nume AND prenume = @Prenume)
	BEGIN
		INSERT INTO Autori(nume,prenume)
		VALUES
			(@Nume,@Prenume)
	END

	SET @id_autor  = (SELECT TOP 1 id FROM Autori WHERE nume = @Nume AND prenume = @Prenume)
	IF EXISTS(SELECT 1 FROM Autori_Carti WHERE id_carte = @ID_carte and id_autor = @id_autor)
	BEGIN
		PRINT 'Autorul este deja asociat unei carti'
	END
	ELSE
	BEGIN
		INSERT INTO Autori_Carti(id_autor,id_carte)
		VALUES
			(@id_autor,@ID_carte)
	END
END
GO

CREATE FUNCTION afiseaza (@nr_autori INT)
RETURNS TABLE
AS
RETURN
(
    SELECT 
        L.nume AS Libraria,
        L.adresa AS Adresa,
        C.titlu AS Titlu,
        COUNT(AC.id_autor) AS NrAutori
    FROM CARTI C
    JOIN Autori_Carti AC ON C.id = AC.id_carte
    JOIN Carti_Librarii CL ON C.id = CL.id_carte
    JOIN Librarii L ON CL.id_librarie = L.id
    GROUP BY L.nume, L.adresa, C.titlu, C.id
    HAVING COUNT(AC.id_autor) = @nr_autori
);
GO

SELECT * FROM dbo.afiseaza(1)
ORDER BY Titlu;