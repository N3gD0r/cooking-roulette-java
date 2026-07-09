CREATE TABLE IF NOT EXISTS "IngredientTypes" (
    id INT NOT NULL,
    type_str VARCHAR(255) NOT NULL,
    CONSTRAINT "PK_ingredient_types_id" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "Ingredients" (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    type_id INT NOT NULL,
    CONSTRAINT "FK_ingredients_type_id"
    FOREIGN KEY
    (type_id)
    REFERENCES "IngredientTypes" (id),
    CONSTRAINT "PK_ingredients_id" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "Recipes" (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    cook_time INT,
    CONSTRAINT "PK_recipes_id" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "RecipeSteps" (
    id UUID NOT NULL,
    step INT NOT NULL,
    instruction VARCHAR(255),
    recipe_id UUID NOT NULL,
    CONSTRAINT "FK_recipe_steps_id"
    FOREIGN KEY
    (recipe_id)
    REFERENCES "Recipes" (id),
    CONSTRAINT "PK_recipe_steps_id" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "RecipesIngredients" (
    recipe_id UUID NOT NULL,
    ingredient_id UUID NOT NULL,
    CONSTRAINT "PK_recipes_ingredients_id" PRIMARY KEY (recipe_id,
    ingredient_id),
    CONSTRAINT "FK_recipe_id"
    FOREIGN KEY
    (recipe_id)
    REFERENCES "Recipes" (id),
    CONSTRAINT "FK_ingredient_id"
    FOREIGN KEY
    (ingredient_id)
    REFERENCES "Ingredients" (id)
);
