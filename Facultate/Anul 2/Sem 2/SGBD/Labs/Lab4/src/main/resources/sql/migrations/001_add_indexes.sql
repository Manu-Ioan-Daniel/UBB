CREATE INDEX IF NOT EXISTS idx_profesori_email ON profesori(email);
CREATE INDEX IF NOT EXISTS idx_profesori_materie_id ON profesori(materie_id);
CREATE INDEX IF NOT EXISTS idx_profesori_age ON profesori(age);
CREATE INDEX IF NOT EXISTS idx_profesori_materie_age ON profesori(materie_id, age);
