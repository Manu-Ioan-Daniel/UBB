CREATE TABLE PlayerCurrencies_Log (
    PlayerID INT,
    CurrencyID INT,
    Amount INT,
    OperationType NVARCHAR(10),
    OperationDate DATETIME,
    LoginName NVARCHAR(128)
);
GO