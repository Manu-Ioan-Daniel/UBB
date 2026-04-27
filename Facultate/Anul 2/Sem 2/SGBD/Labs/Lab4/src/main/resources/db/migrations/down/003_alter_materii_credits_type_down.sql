-- Down: Revert materii.credits to INT. Ensure values fit into INT before running.
ALTER TABLE materii ALTER COLUMN credits TYPE INT USING credits::int;

