USE LostArkDB
GO
CREATE PROCEDURE P_SetDBVersion
	@Version DECIMAL(3,1)
AS
BEGIN
	IF @Version < 1.0 OR @Version > 1.5
	BEGIN
		PRINT 'Esti prost';
		RETURN 
	END
	
	DECLARE @CurrentVersion DECIMAL(3,1)
	SELECT TOP 1 @CurrentVersion=VersionNumber FROM DBVersion ORDER BY VersionNumber DESC;
	IF @Version < @CurrentVersion
	BEGIN
		WHILE @CurrentVersion > @Version
		BEGIN
			IF @CurrentVersion = 1.5 EXEC P_UndoAddEquipmentForeignKey;
			ELSE IF @CurrentVersion = 1.4 EXEC P_UndoAdaugareColoanaCharacters;
			ELSE IF @CurrentVersion = 1.3 EXEC P_UndoCreateTablePlayerLogins;
			ELSE IF @CurrentVersion = 1.2 EXEC P_UndoAdaugaConstraintDefaultGuildCreationDate;
			ELSE IF @CurrentVersion = 1.1 EXEC  P_UndoModificaColoanaEquipmentName;
			DELETE FROM DBVersion WHERE VersionNumber = @CurrentVersion;
			SELECT TOP 1 @CurrentVersion = VersionNumber FROM DBVersion ORDER BY VersionNumber DESC;
		END
	END
	ELSE IF @Version > @CurrentVersion 
	BEGIN
		WHILE @CurrentVersion < @Version
		BEGIN
			IF @CurrentVersion = 1.0 EXEC P_ModificaColoanaEquipmentName;
			ELSE IF @CurrentVersion = 1.1 EXEC P_AdaugaConstraintDefaultGuildCreationDate;
			ELSE IF @CurrentVersion = 1.2 EXEC P_CreateTablePlayerLogins;
			ELSE IF @CurrentVersion = 1.3 EXEC P_AdaugareColoanaCharacters;
			ELSE IF @CurrentVersion = 1.4 EXEC P_AddEquipmentForeignKey;
			INSERT INTO DBVersion VALUES(@CurrentVersion + 0.1);
			SET @CurrentVersion = @CurrentVersion + 0.1;
		END
	END
END


