CREATE TABLE CharacterEquipments_Log (
    CharacterID INT,
    EquipmentID INT,
    OperationType NVARCHAR(10),
    OperationDate DATETIME,
    LoginName NVARCHAR(128)
);
GO

CREATE TABLE Characters_Log (
    CharacterID INT,
    PlayerID INT,
    CharacterName NVARCHAR(50),
    GuildID INT,
    Class NVARCHAR(50),
    GuildRole NVARCHAR(100),
    GuildJoinDate DATE,
    OperationType NVARCHAR(10),
    OperationDate DATETIME,
    LoginName NVARCHAR(128)
);
GO

CREATE TABLE Equipments_Log (
    EquipmentID INT,
    EquipmentRarity NVARCHAR(50),
    EquipmentName NVARCHAR(50),
    EquipmentSlot NVARCHAR(50),
    EquipmentCategoryID INT,
    OperationType NVARCHAR(10),
    OperationDate DATETIME,
    LoginName NVARCHAR(128)
);
GO


