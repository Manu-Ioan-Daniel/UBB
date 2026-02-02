CREATE PROCEDURE adaugaNota
	@RestaurantID INT,
	@UtilizatorID INT,
	@Nota INT
AS
BEGIN
	IF EXISTS(SELECT 1 FROM Note_Restaurant WHERE restaurant_id = @RestaurantID AND utilizator_id = @UtilizatorID)
	BEGIN
		UPDATE Note_Restaurant
		SET nota = @Nota
		WHERE restaurant_id = @RestaurantID AND utilizator_id = @UtilizatorID
	END
	ELSE
	BEGIN
		INSERT INTO Note_Restaurant(restaurant_id,utilizator_id,nota)
		VALUES (@RestaurantID,@UtilizatorID,@Nota)
	END
END;
GO

CREATE PROCEDURE afiseaza
	@Email VARCHAR(100)
AS
BEGIN
	SELECT T.nume,R.nume,R.nr_telefon,O.nume,RS.nota,U.nume,U.email
	FROM Utilizatori U
	JOIN Note_Restaurant RS ON U.id = RS.utilizator_id
	JOIN Restaurante R on RS.restaurant_id = R.id
	JOIN Orase O on O.id = R.oras_id
	JOIN Tip_Restaurante T ON T.id = R.tip_restaurant_id
	WHERE U.email = @Email
END;
GO