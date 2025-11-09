USE LostArkDB
GO
CREATE PROCEDURE P_ModificaColoanaEquipmentName
AS
BEGIN
    ALTER TABLE Equipments
    ALTER COLUMN EquipmentName NVARCHAR(100);
END;
GO
CREATE PROCEDURE P_UndoModificaColoanaEquipmentName
AS
BEGIN
    ALTER TABLE Equipments
    ALTER COLUMN EquipmentName NVARCHAR(50);

END;
GO

CREATE PROCEDURE P_AdaugaConstraintDefaultGuildCreationDate
AS
BEGIN
    ALTER TABLE Guilds
    ADD CONSTRAINT DF_GuildCreateDate DEFAULT GETDATE() FOR GuildCreationDate;
END;
GO

CREATE PROCEDURE P_UndoAdaugaConstraintDefaultGuildCreationDate
AS
BEGIN
    ALTER TABLE Guilds
    DROP CONSTRAINT DF_GuildCreateDate;

END;
GO


CREATE PROCEDURE P_CreateTablePlayerLogins
AS
BEGIN
    CREATE TABLE PlayerLogins (
        LoginID INT PRIMARY KEY IDENTITY,
        PlayerID INT FOREIGN KEY REFERENCES Players(PlayerID),
        LoginDate DATETIME DEFAULT GETDATE()
    );
END;
GO

CREATE PROCEDURE P_UndoCreateTablePlayerLogins
AS
BEGIN
    DROP TABLE PlayerLogins;

END;
GO


CREATE PROCEDURE P_AdaugareColoanaCharacters
AS
BEGIN
    ALTER TABLE Characters
    ADD CharacterLevel INT;

END;
GO

CREATE PROCEDURE P_UndoAdaugareColoanaCharacters
AS
BEGIN
    ALTER TABLE Characters
    DROP COLUMN CharacterLevel;

END;
GO


CREATE PROCEDURE P_AddEquipmentForeignKey
AS
BEGIN
    ALTER TABLE Equipments
    ADD CONSTRAINT FK_Equipments_Categories FOREIGN KEY (EquipmentCategoryID)
    REFERENCES EquipmentCategories(EquipmentCategoryID);

END;
GO

CREATE PROCEDURE P_UndoAddEquipmentForeignKey
AS
BEGIN
    ALTER TABLE Equipments
    DROP CONSTRAINT FK_Equipments_Categories;

END;
GO

