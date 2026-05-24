USE programare_web;

CREATE TABLE IF NOT EXISTS programe_saptamanale (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ziua VARCHAR(20) NOT NULL,
    exercitiu VARCHAR(80) NOT NULL,
    seturi INT NOT NULL,
    repetari INT NOT NULL,
    focus VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO programe_saptamanale (ziua, exercitiu, seturi, repetari, focus)
SELECT * FROM (
    SELECT 'Luni' AS ziua, 'Flotari' AS exercitiu, 3 AS seturi, 15 AS repetari, 'Forta' AS focus
    UNION ALL SELECT 'Luni', 'Genuflexiuni', 3, 20, 'Rezistenta'
    UNION ALL SELECT 'Marti', 'Tractiuni', 3, 10, 'Upper Body'
    UNION ALL SELECT 'Marti', 'Dips', 3, 12, 'Triceps'
    UNION ALL SELECT 'Joi', 'Fandari', 3, 12, 'Lower Body'
    UNION ALL SELECT 'Joi', 'Plank', 3, 60, 'Core'
    UNION ALL SELECT 'Vineri', 'Muscle Up', 3, 5, 'Full Body'
    UNION ALL SELECT 'Duminica', 'L-Sit', 3, 20, 'Core'
) AS seed
WHERE NOT EXISTS (SELECT 1 FROM programe_saptamanale LIMIT 1);
