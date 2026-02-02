
EXEC adaugaNota @RestaurantID = 1, @UtilizatorID = 1, @Nota = 9.5;
EXEC adaugaNota @RestaurantID = 3, @UtilizatorID = 1, @Nota = 8.0;
EXEC adaugaNota @RestaurantID = 1, @UtilizatorID = 2, @Nota = 10.0;
SELECT * FROM Note_Restaurant;
EXEC adaugaNota @RestaurantID = 1, @UtilizatorID = 1, @Nota = 10.0;
SELECT * FROM Note_Restaurant;
EXEC afiseaza @Email = 'ion@gmail.com';
