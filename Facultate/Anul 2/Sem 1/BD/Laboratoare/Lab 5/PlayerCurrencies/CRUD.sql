CREATE PROCEDURE dbo.createPlayerCurrency
    @PlayerID INT,
    @CurrencyID INT,
    @Amount INT,
    @Result INT OUTPUT
AS
BEGIN
    IF dbo.isValidPlayerCurrency(@PlayerID, @CurrencyID, @Amount) = 0
    BEGIN
        SET @Result = -1;
        RETURN;
    END

    INSERT INTO PlayerCurrencies (PlayerID, CurrencyID, Amount)
    VALUES (@PlayerID, @CurrencyID, @Amount);

    SET @Result = 1;
END;
GO

CREATE PROCEDURE dbo.readPlayerCurrency
    @PlayerID INT,
    @CurrencyID INT
AS
BEGIN
    SELECT *
    FROM PlayerCurrencies
    WHERE PlayerID = @PlayerID
      AND CurrencyID = @CurrencyID;
END;
GO

CREATE PROCEDURE dbo.updatePlayerCurrency
    @PlayerID INT,
    @CurrencyID INT,
    @Amount INT,
    @Result INT OUTPUT
AS
BEGIN
    IF dbo.isValidPlayerCurrency(@PlayerID, @CurrencyID, @Amount) = 0
    BEGIN
        SET @Result = -1;
        RETURN;
    END

    UPDATE PlayerCurrencies
    SET Amount = @Amount
    WHERE PlayerID = @PlayerID
      AND CurrencyID = @CurrencyID;

    SET @Result = 1;
END;
GO

CREATE PROCEDURE dbo.deletePlayerCurrency
    @PlayerID INT,
    @CurrencyID INT
AS
BEGIN
    DELETE FROM PlayerCurrencies
    WHERE PlayerID = @PlayerID
      AND CurrencyID = @CurrencyID;
END;
GO
