CREATE TRIGGER dbo.playerCurrenciesTrigger
ON PlayerCurrencies
AFTER UPDATE, DELETE
AS
BEGIN
    INSERT INTO PlayerCurrencies_Log
    SELECT
        d.PlayerID,
        d.CurrencyID,
        d.Amount,
        CASE
            WHEN i.PlayerID IS NULL THEN 'DELETE'
            ELSE 'UPDATE'
        END,
        GETDATE(),
        SUSER_SNAME()
    FROM deleted d
    LEFT JOIN inserted i
        ON d.PlayerID = i.PlayerID
       AND d.CurrencyID = i.CurrencyID;
END;
GO