CREATE PROCEDURE adaugaStatie
	@RutaID INT,
	@StatieID INT,
	@Ora_Sosire TIME,
	@Ora_Plecare TIME
AS
BEGIN
	IF EXISTS(SELECT 1 FROM Rute_Statii WHERE ruta_id = @RutaID AND statie_id = @StatieID)
	BEGIN
		UPDATE Rute_Statii
		SET ora_plecare = @Ora_Plecare, ora_sosire = @Ora_Sosire
		WHERE ruta_id = @RutaID AND statie_id = @StatieID
	END
	ELSE
	BEGIN
	INSERT INTO Rute_Statii(ruta_id,statie_id,ora_plecare,ora_sosire)
	VALUES
		(@RutaID,@StatieID,@Ora_Plecare,@Ora_Sosire)
	END
END;
GO

CREATE VIEW numeRute
AS
SELECT R.nume
FROM Ruta R
JOIN Rute_Statii RS ON R.id = RS.ruta_id
GROUP BY R.nume, R.id
HAVING COUNT(RS.statie_id) = (SELECT COUNT(*) FROM Statii);
