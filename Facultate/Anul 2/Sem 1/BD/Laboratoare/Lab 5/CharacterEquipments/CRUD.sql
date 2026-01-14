--CRUD pt Character
CREATE OR ALTER PROCEDURE dbo.createCharacter
    @PlayerID INT,
    @CharacterName NVARCHAR(50),
    @GuildID INT,
    @Class NVARCHAR(50),
    @GuildRole NVARCHAR(100),
    @GuildJoinDate DATE,
    @Result INT OUTPUT
AS
BEGIN
    IF dbo.isValidCharacter
    (
        @PlayerID,
        @CharacterName,
        @GuildID,
        @Class,
        @GuildRole,
        @GuildJoinDate
    ) = 0
    BEGIN
        SET @Result = -1;
        RETURN;
END

    INSERT INTO Characters
    (PlayerID, CharacterName, GuildID, Class, GuildRole, GuildJoinDate)
    VALUES
    (@PlayerID, @CharacterName, @GuildID, @Class, @GuildRole, @GuildJoinDate);

    SET @Result = 1;
END;
GO

CREATE OR ALTER PROCEDURE dbo.readCharacter
    @CharacterID INT
AS
BEGIN
    SELECT *
    FROM Characters
    WHERE CharacterID = @CharacterID;
END;
GO

CREATE OR ALTER PROCEDURE dbo.updateCharacter
    @CharacterID INT,
    @CharacterName NVARCHAR(50),
    @GuildRole NVARCHAR(100),
    @Result INT OUTPUT
AS
BEGIN
    IF dbo.isValidCharacter(1,@CharacterName,1,'salut',@GuildRole,GETDATE()) = 0
    BEGIN
        SET @Result = -1;
        RETURN;
    END

    UPDATE Characters
    SET CharacterName = @CharacterName,
        GuildRole = @GuildRole
    WHERE CharacterID = @CharacterID;

    SET @Result = 1;
END;
GO

CREATE  OR ALTER PROCEDURE dbo.deleteCharacter
    @CharacterID INT
AS
BEGIN
    IF dbo.isValidId(@CharacterID) = 1 AND EXISTS (SELECT 1 FROM Characters WHERE CharacterID = @CharacterID)
        BEGIN
             DELETE FROM Characters
             WHERE CharacterID = @CharacterID;
        END
    
END;
GO

--CRUD pt Equipments

CREATE OR ALTER PROCEDURE dbo.createEquipment
    @EquipmentRarity NVARCHAR(50),
    @EquipmentName NVARCHAR(50),
    @EquipmentSlot NVARCHAR(50),
    @EquipmentCategoryID INT,
    @Result INT OUTPUT
AS
BEGIN
    IF dbo.isValidEquipment(@EquipmentRarity,@EquipmentName,@EquipmentSlot,@EquipmentCategoryID) = 0
    BEGIN
        SET @Result = -1;
        RETURN;
    END

    INSERT INTO Equipments
    (EquipmentRarity, EquipmentName, EquipmentSlot, EquipmentCategoryID)
    VALUES
    (@EquipmentRarity, @EquipmentName, @EquipmentSlot, @EquipmentCategoryID);

    SET @Result = 1;
END;
GO

CREATE OR ALTER PROCEDURE dbo.readEquipment
    @EquipmentID INT
AS
BEGIN
    
    SELECT *
    FROM Equipments
    WHERE EquipmentID = @EquipmentID;
END;
GO

CREATE OR ALTER PROCEDURE dbo.updateEquipment
    @EquipmentID INT,
    @EquipmentRarity NVARCHAR(50),
    @EquipmentName NVARCHAR(50),
    @Result INT OUTPUT
AS
BEGIN
    IF dbo.isValidID(@EquipmentID) = 0
       OR NOT EXISTS (SELECT 1 FROM Equipments WHERE EquipmentID = @EquipmentID)
       OR dbo.isValidEquipment(@EquipmentRarity,@EquipmentName,'slot',1) = 0
    BEGIN
        SET @Result = -1;
        RETURN;
    END

    UPDATE Equipments
    SET EquipmentRarity = @EquipmentRarity,
        EquipmentName = @EquipmentName
    WHERE EquipmentID = @EquipmentID;

    SET @Result = 1;
END;
GO

CREATE OR ALTER PROCEDURE dbo.deleteEquipment
    @EquipmentID INT
AS
BEGIN
    IF dbo.isValidID(@EquipmentID) = 1
       AND EXISTS (SELECT 1 FROM Equipments WHERE EquipmentID = @EquipmentID)
        DELETE FROM Equipments WHERE EquipmentID = @EquipmentID;
END;
GO

--CRUD pt CharacterEquipments
CREATE OR ALTER PROCEDURE dbo.createCharacterEquipment
    @CharacterID INT,
    @EquipmentID INT,
    @Result INT OUTPUT
AS
BEGIN
    IF dbo.isValidCharacterEquipment(@CharacterID, @EquipmentID) = 0
    BEGIN
        SET @Result = -1;
        RETURN;
    END

    INSERT INTO CharacterEquipments (CharacterID, EquipmentID)
    VALUES (@CharacterID, @EquipmentID);

    SET @Result = 1;
END;
GO

CREATE OR ALTER PROCEDURE dbo.readCharacterEquipment
    @CharacterID INT,
    @EquipmentID INT
AS
BEGIN
    SELECT *
    FROM CharacterEquipments
    WHERE CharacterID = @CharacterID
      AND EquipmentID = @EquipmentID;
END;
GO

CREATE OR ALTER PROCEDURE dbo.updateCharacterEquipment
    @CharacterID INT,
    @EquipmentID INT,
    @Result INT OUTPUT
AS
BEGIN
   
    IF dbo.isValidCharacterEquipment(@CharacterID, @EquipmentID) = 0
    BEGIN
        SET @Result = -1;
        RETURN;
    END
    SET @Result = 1;
END;
GO

CREATE OR ALTER PROCEDURE dbo.deleteCharacterEquipment
    @CharacterID INT,
    @EquipmentID INT
AS
BEGIN
    IF dbo.isValidID(@EquipmentID) = 1 AND dbo.isValidID(@CharacterID) = 1
       AND EXISTS (SELECT 1 FROM CharacterEquipments WHERE EquipmentID = @EquipmentID AND CharacterID = @CharacterID)
        DELETE FROM Equipments WHERE EquipmentID = @EquipmentID;
    DELETE FROM CharacterEquipments
    WHERE CharacterID = @CharacterID
      AND EquipmentID = @EquipmentID;
END;
GO
