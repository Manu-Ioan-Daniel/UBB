CREATE TABLE CharacterRaids_Log
(
    CharacterID INT,
    RaidID INT,
    CompletionStatus BIT,
    CompletionDate DATE,
    OperationType NVARCHAR(10),
    OperationDate DATETIME,
    LoginName NVARCHAR(100)
);
GO