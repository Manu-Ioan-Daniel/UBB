DELETE FROM Equipments_Log;
DELETE FROM Characters_Log;
DELETE FROM CharacterEquipments_Log;

DECLARE @Result INT;
EXEC dbo.createCharacterEquipment 
    @CharacterID = 1, 
    @EquipmentID = 1, 
    @Result = @Result OUTPUT;
SELECT 'CREATE CharacterEquipment Result:' AS Operation, @Result AS Status;

EXEC dbo.readCharacterEquipment @CharacterID = 1, @EquipmentID = 1;

DECLARE @Result2 INT;
EXEC dbo.updateCharacterEquipment 
    @CharacterID = 1, 
    @EquipmentID = 1, 
    @Result = @Result2 OUTPUT;
SELECT 'UPDATE CharacterEquipment Result:' AS Operation, @Result2 AS Status;

EXEC dbo.readCharacterEquipment @CharacterID = 1, @EquipmentID = 1;

EXEC dbo.deleteCharacterEquipment @CharacterID = 1, @EquipmentID = 1;

SELECT * FROM CharacterEquipments_Log;

DECLARE @CharResult INT;
DECLARE @JoinDate DATE = GETDATE();

EXEC dbo.createCharacter 
    @PlayerID = 1,
    @CharacterName = N'TestChar',
    @GuildID = 1,
    @Class = N'Warrior',
    @GuildRole = N'Member',
    @GuildJoinDate = @JoinDate,
    @Result = @CharResult OUTPUT;
SELECT 'CREATE Character Result:' AS Operation, @CharResult AS Status;

EXEC dbo.readCharacter @CharacterID = 1;

DECLARE @CharResult2 INT;
EXEC dbo.updateCharacter 
    @CharacterID = 1,
    @CharacterName = N'TestCharUpdated',
    @GuildRole = N'Leader',
    @Result = @CharResult2 OUTPUT;
SELECT 'UPDATE Character Result:' AS Operation, @CharResult2 AS Status;


EXEC dbo.readCharacter  @CharacterID = 1;
    

EXEC dbo.deleteCharacter  @CharacterID = 1;

SELECT * FROM Characters_Log;

DECLARE @EquipResult INT;
EXEC dbo.createEquipment
    @EquipmentRarity = N'Rare',
    @EquipmentName = N'TestSword',
    @EquipmentSlot = N'Weapon',
    @EquipmentCategoryID = 1,
    @Result = @EquipResult OUTPUT;
SELECT 'CREATE Equipment Result:' AS Operation, @EquipResult AS Status;

EXEC dbo.readEquipment @EquipmentID = 1;

DECLARE @EquipResult2 INT;
EXEC dbo.updateEquipment
    @EquipmentID = 1,
    @EquipmentRarity = N'Epic',
    @EquipmentName = N'TestSwordUpdated',
    @Result = @EquipResult2 OUTPUT;
SELECT 'UPDATE Equipment Result:' AS Operation, @EquipResult2 AS Status;

EXEC dbo.readEquipment @EquipmentID = 1;

EXEC dbo.deleteEquipment @EquipmentID = 1;

SELECT * FROM Equipments_Log;
