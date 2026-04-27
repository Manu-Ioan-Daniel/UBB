-- Up: Add indexes to improve query performance
CREATE INDEX IF NOT EXISTS idx_profesori_materie ON profesori(materie_id);
CREATE INDEX IF NOT EXISTS idx_profesori_email ON profesori(email);
CREATE INDEX IF NOT EXISTS idx_note_studenti_materie ON note_studenti(materie_id);

