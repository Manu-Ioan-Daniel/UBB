CREATE FUNCTION dbo.isValidCharacterEquipment
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