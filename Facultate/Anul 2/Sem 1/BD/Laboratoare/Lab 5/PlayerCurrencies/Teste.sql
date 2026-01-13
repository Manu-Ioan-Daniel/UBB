DECLARE @Result INT;
EXEC dbo.createPlayerCurrency @PlayerID=1, @CurrencyID=1, @Amount=100, @Result=@Result OUTPUT;
SELECT 'CREATE Result:' AS Operation, @Result AS Status;

EXEC dbo.readPlayerCurrency @PlayerID=1, @CurrencyID=1;

DECLARE @Result2 INT;
EXEC dbo.updatePlayerCurrency @PlayerID=1, @CurrencyID=1, @Amount=200, @Result=@Result2 OUTPUT;
SELECT 'UPDATE Result:' AS Operation, @Result2 AS Status;

EXEC dbo.readPlayerCurrency @PlayerID=1, @CurrencyID=1;

EXEC dbo.deletePlayerCurrency @PlayerID=1, @CurrencyID=1;

SELECT * FROM PlayerCurrencies_Log;

