CREATE OR ALTER FUNCTION dbo.isValidCharacterEquipment
(
    @CharacterID INT,
    @EquipmentID INT
)
RETURNS BIT
AS
BEGIN
    IF dbo.isValidID(@CharacterID) = 0 RETURN 0;
    IF dbo.isValidID(@EquipmentID) = 0 RETURN 0;
    RETURN 1;
END;
GO

CREATE OR ALTER FUNCTION dbo.isValidCharacter
(
    @PlayerID INT,
    @CharacterName NVARCHAR(50),
    @GuildID INT,
    @Class NVARCHAR(50),
    @GuildRole NVARCHAR(100),
    @GuildJoinDate DATE
)
RETURNS BIT
AS
BEGIN
  
    IF dbo.isValidID(@PlayerID) = 0 RETURN 0;
    IF NOT EXISTS (SELECT 1 FROM Players WHERE PlayerID = @PlayerID) RETURN 0;


    IF @CharacterName IS NULL OR LEN(@CharacterName) = 0 RETURN 0;
    IF LEN(@CharacterName) > 50 RETURN 0;


    IF dbo.isValidID(@GuildID) = 0 RETURN 0;
    IF NOT EXISTS (SELECT 1 FROM Guilds WHERE GuildID = @GuildID) RETURN 0;


    IF @Class IS NULL OR LEN(@Class) = 0 RETURN 0;
    IF LEN(@Class) > 50 RETURN 0;

   
    IF @GuildRole IS NOT NULL AND LEN(@GuildRole) > 100 RETURN 0;

 
    IF @GuildJoinDate IS NOT NULL AND @GuildJoinDate > GETDATE() RETURN 0;

    RETURN 1;
END;
GO

CREATE OR ALTER FUNCTION dbo.isValidEquipment
(
    @EquipmentRarity NVARCHAR(50),
    @EquipmentName NVARCHAR(50),
    @EquipmentSlot NVARCHAR(50),
    @EquipmentCategoryID INT
)
RETURNS BIT
AS
BEGIN
    IF @EquipmentRarity IS NULL OR LEN(@EquipmentRarity) = 0 OR LEN(@EquipmentRarity) > 50 RETURN 0;
    IF @EquipmentName IS NULL OR LEN(@EquipmentName) = 0 OR LEN(@EquipmentName) > 50 RETURN 0;
    IF @EquipmentSlot IS NULL OR LEN(@EquipmentSlot) = 0 OR LEN(@EquipmentSlot) > 50 RETURN 0;
    IF dbo.isValidID(@EquipmentCategoryID) = 0 RETURN 0;
    IF NOT EXISTS (SELECT 1 FROM EquipmentCategories WHERE EquipmentCategoryID = @EquipmentCategoryID) RETURN 0;
    RETURN 1;
END;
GO