CREATE FUNCTION dbo.isValidPlayerCurrency
(
    @PlayerID INT,
    @CurrencyID INT,
    @Amount INT
)
RETURNS BIT
AS
BEGIN
    IF dbo.isValidID(@PlayerID) = 0 RETURN 0;
    IF dbo.isValidID(@CurrencyID) = 0 RETURN 0;
    IF @Amount < 0 RETURN 0;
    RETURN 1;
END;
GO