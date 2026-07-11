ALTER TABLE recipe
    ADD CONSTRAINT uc_recipe_name
        UNIQUE (name);