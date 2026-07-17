package org.n3gd0r.recipe.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.domain.IngredientEnum;
import org.n3gd0r.recipe.domain.Mass;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeId;
import org.n3gd0r.recipe.domain.RecipeIngredient;
import org.n3gd0r.recipe.domain.RecipeIngredientMother;
import org.n3gd0r.recipe.domain.RecipeInstruction;
import org.n3gd0r.recipe.domain.RecipeInstructionMother;
import org.n3gd0r.recipe.domain.RecipeMother;
import org.n3gd0r.recipe.domain.exception.RecipeNotFoundException;
import org.n3gd0r.recipe.repository.InMemoryRecipeRepository;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.get.GetRecipeParameters;
import org.n3gd0r.recipe.usecase.get.GetRecipeQuery;

public class GetRecipeTest {
    private RecipeRepository recipeRepository;
    private GetRecipeQuery getRecipeQuery;
    private RecipeId recipeIdToFetch;
    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipeRepository = new InMemoryRecipeRepository();
        getRecipeQuery = new GetRecipeQuery(recipeRepository);
        recipeIdToFetch = new RecipeId(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));

        List<RecipeIngredient> ingredients = Arrays.asList(
                RecipeIngredientMother.recipeIngredient()
                        .ingredientName("huevos")
                        .ingredientType(IngredientEnum.CARNES)
                        .weight(Mass.ofGrams(180))
                        .build());
        List<RecipeInstruction> instructions = Arrays.asList(
                RecipeInstructionMother.recipeInstruction()
                        .instructionNumber(1)
                        .instruction("En agua hirviendo, colocar los huevos durante 15 minutos")
                        .build());
        recipe = RecipeMother.recipe()
                .id(recipeIdToFetch)
                .name("huevos cocidos")
                .cookTime(20)
                .ingredients(ingredients)
                .instructions(instructions)
                .build();

        recipeRepository.save(recipe);
    }

    @Test
    void testGetRecipeById() {
        GetRecipeParameters parameters = new GetRecipeParameters(recipeIdToFetch);

        Recipe recipe = getRecipeQuery.execute(parameters);

        assertThat(recipe).isNotNull();
        assertThat(recipe).isEqualTo(this.recipe);
    }

    @Test
    void testGetRecipeByName() {
        GetRecipeParameters parameters = new GetRecipeParameters("huevos cocidos");

        Recipe recipe = getRecipeQuery.execute(parameters);

        assertThat(recipe).isNotNull();
        assertThat(recipe).isEqualTo(this.recipe);
        assertThat(recipe.getName()).isEqualToIgnoringCase(this.recipe.getName());
    }

    @Test
    void testGetRecibeWithWrongIdThrowsException() {
        GetRecipeParameters parameters = new GetRecipeParameters(new RecipeId(UUID.randomUUID()));

        assertThatExceptionOfType(RecipeNotFoundException.class)
                .isThrownBy(() -> getRecipeQuery.execute(parameters));
    }

    @Test
    void testGetRecibeWithWrongNameThrowsException() {
        GetRecipeParameters parameters = new GetRecipeParameters("Definetly not a name 123");

        assertThatExceptionOfType(RecipeNotFoundException.class)
                .isThrownBy(() -> getRecipeQuery.execute(parameters));
    }
}
