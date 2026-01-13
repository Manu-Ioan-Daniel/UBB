DECLARE @Result INT;

EXEC dbo.createCharacterRaid
    @CharacterID = 19,
    @RaidID = 2,
    @CompletionStatus = 0,
    @CompletionDate = NULL,
    @Result = @Result OUTPUT;

SELECT 'CREATE Result:' AS Operation, @Result AS Status;

EXEC dbo.readCharacterRaid
    @CharacterID = 19,
    @RaidID = 2;

DECLARE @Result_2 INT;

EXEC dbo.updateCharacterRaid
    @CharacterID = 19,
    @RaidID = 2,
    @CompletionStatus = 1,
    @CompletionDate = '2023-06-01',
    @Result = @Result_2 OUTPUT;

SELECT 'UPDATE Result:' AS Operation, @Result_2 AS Status;

EXEC dbo.readCharacterRaid
    @CharacterID = 19,
    @RaidID = 2;

EXEC dbo.deleteCharacterRaid
    @CharacterID = 19,
    @RaidID = 2;

SELECT * FROM CharacterRaids_Log


