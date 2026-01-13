ALTER TABLE CharacterRaids
ADD CONSTRAINT CK_CharacterRaids_CompletionDate
CHECK (
    (CompletionStatus = 0 AND CompletionDate IS NULL)
 OR (CompletionStatus = 1 AND CompletionDate IS NOT NULL)
);
GO

CREATE FUNCTION dbo.isValidCharacterRaid
(
    @CharacterID INT,
    @RaidID INT,
    @CompletionStatus BIT,
    @CompletionDate DATE
)
RETURNS BIT
AS
BEGIN

    IF dbo.isValidID(@CharacterID) = 0 RETURN 0;
    IF dbo.isValidID(@RaidID) = 0 RETURN 0;

    IF (@CompletionStatus = 1 AND @CompletionDate IS NULL) RETURN 0;
    IF (@CompletionStatus = 0 AND @CompletionDate IS NOT NULL) RETURN 0;

    RETURN 1;
END;
GO

