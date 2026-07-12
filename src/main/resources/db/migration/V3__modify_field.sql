ALTER TABLE recipe
    ALTER COLUMN cook_time TYPE INT USING cook_time::INT;
