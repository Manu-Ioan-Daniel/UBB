CREATE VIEW dbo.playerCurrenciesPositive
AS
SELECT p.PlayerUsername, c.CurrencyName, pc.Amount
FROM PlayerCurrencies pc
JOIN Players p ON pc.PlayerID = p.PlayerID
JOIN Currencies c ON pc.CurrencyID = c.CurrencyID
WHERE pc.Amount > 0;
GO

CREATE VIEW dbo.playerCurrenciesZero
AS
SELECT p.PlayerUsername, c.CurrencyName, pc.Amount
FROM PlayerCurrencies pc
JOIN Players p ON pc.PlayerID = p.PlayerID
JOIN Currencies c ON pc.CurrencyID = c.CurrencyID
WHERE pc.Amount = 0;
GO