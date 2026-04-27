-- Up: Add phone column to profesori
ALTER TABLE profesori ADD COLUMN phone VARCHAR(20);

-- Ensure existing rows have NULL or default
UPDATE profesori SET phone = NULL WHERE phone IS NULL;

