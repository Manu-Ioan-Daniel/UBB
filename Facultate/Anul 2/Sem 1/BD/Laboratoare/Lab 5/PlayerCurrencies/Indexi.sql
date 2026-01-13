CREATE NONCLUSTERED INDEX IX_PlayerCurrencies_Player ON PlayerCurrencies(PlayerID);
CREATE NONCLUSTERED INDEX IX_PlayerCurrencies_Amount ON PlayerCurrencies(Amount);
CREATE NONCLUSTERED INDEX IX_PlayerCurrencies_Currency ON PlayerCurrencies(CurrencyID);

SELECT * FROM dbo.playerCurrenciesPositive
SELECT * FROM dbo.player1Currencies 

