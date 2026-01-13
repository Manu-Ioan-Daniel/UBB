CREATE PROCEDURE dbo.createCharacterRaid
    @CharacterID INT,
    @RaidID INT,
    @CompletionStatus BIT,
    @CompletionDate DATE,
    @Result INT OUTPUT
AS
BEGIN
    IF dbo.isValidCharacterRaid
       (@CharacterID, @RaidID, @CompletionStatus, @CompletionDate) = 0
    BEGIN
        SET @Result = -1;
        RETURN;
    END

    INSERT INTO CharacterRaids
    VALUES (@CharacterID, @RaidID, @CompletionStatus, @CompletionDate);

    SET @Result = 1;
END;
GO

CREATE PROCEDURE dbo.readCharacterRaid
    @CharacterID INT,
    @RaidID INT
AS
BEGIN
    SELECT *
    FROM CharacterRaids
    WHERE CharacterID = @CharacterID
      AND RaidID = @RaidID;
END;
GO

CREATE PROCEDURE dbo.updateCharacterRaid
    @CharacterID INT,
    @RaidID INT,
    @CompletionStatus BIT,
    @CompletionDate DATE,
    @Result INT OUTPUT
AS
BEGIN
    IF dbo.isValidCharacterRaid
       (@CharacterID, @RaidID, @CompletionStatus, @CompletionDate) = 0
    BEGIN
        SET @Result = -1;
        RETURN;
    END

    UPDATE CharacterRaids
    SET CompletionStatus = @CompletionStatus,
        CompletionDate = @CompletionDate
    WHERE CharacterID = @CharacterID
      AND RaidID = @RaidID;

    SET @Result = 1;
END;
GO

CREATE PROCEDURE dbo.deleteCharacterRaid
    @CharacterID INT,
    @RaidID INT
AS
BEGIN
    DELETE FROM CharacterRaids
    WHERE CharacterID = @CharacterID
      AND RaidID = @RaidID;
END;
GO