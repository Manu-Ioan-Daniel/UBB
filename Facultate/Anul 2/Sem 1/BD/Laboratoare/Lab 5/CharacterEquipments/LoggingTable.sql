CREATE TABLE CharacterEquipments_Log (
    CharacterID INT,
    EquipmentID INT,
    OperationType NVARCHAR(10),
    OperationDate DATETIME,
    LoginName NVARCHAR(128)
);
GO