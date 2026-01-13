CREATE PROCEDURE dbo.createCharacterEquipment
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

CREATE PROCEDURE dbo.readCharacterEquipment
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

CREATE PROCEDURE dbo.updateCharacterEquipment
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

CREATE PROCEDURE dbo.deleteCharacterEquipment
    @CharacterID INT,
    @EquipmentID INT
AS
BEGIN
    DELETE FROM CharacterEquipments
    WHERE CharacterID = @CharacterID
      AND EquipmentID = @EquipmentID;
END;
GO
