ALTER TABLE recipe_ingredient
    ALTER COLUMN weight TYPE INT USING weight::INT;
