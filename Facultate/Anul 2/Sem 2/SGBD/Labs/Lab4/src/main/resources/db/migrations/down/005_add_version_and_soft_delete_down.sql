-- Down: Remove version and soft-delete columns
ALTER TABLE profesori DROP COLUMN IF EXISTS version;
ALTER TABLE profesori DROP COLUMN IF EXISTS is_deleted;
ALTER TABLE profesori DROP COLUMN IF EXISTS deleted_at;
ALTER TABLE profesori DROP COLUMN IF EXISTS deleted_by;

ALTER TABLE studenti DROP COLUMN IF EXISTS version;
ALTER TABLE studenti DROP COLUMN IF EXISTS is_deleted;
ALTER TABLE studenti DROP COLUMN IF EXISTS deleted_at;
ALTER TABLE studenti DROP COLUMN IF EXISTS deleted_by;

ALTER TABLE materii DROP COLUMN IF EXISTS version;
ALTER TABLE materii DROP COLUMN IF EXISTS is_deleted;
ALTER TABLE materii DROP COLUMN IF EXISTS deleted_at;
ALTER TABLE materii DROP COLUMN IF EXISTS deleted_by;

