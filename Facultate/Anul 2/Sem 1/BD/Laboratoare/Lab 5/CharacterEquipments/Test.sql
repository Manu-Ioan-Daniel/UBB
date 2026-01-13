DECLARE @Result INT;
EXEC dbo.createCharacterEquipment @CharacterID=1, @EquipmentID=1, @Result=@Result OUTPUT;
SELECT 'CREATE Result:' AS Operation, @Result AS Status;

EXEC dbo.readCharacterEquipment @CharacterID=1, @EquipmentID=1;

DECLARE @Result2 INT;
EXEC dbo.updateCharacterEquipment @CharacterID=1, @EquipmentID=1, @Result=@Result2 OUTPUT;
SELECT 'UPDATE Result:' AS Operation, @Result2 AS Status;

EXEC dbo.readCharacterEquipment @CharacterID=1, @EquipmentID=1;

EXEC dbo.deleteCharacterEquipment @CharacterID=1, @EquipmentID=1;

SELECT * FROM CharacterEquipments_Log;