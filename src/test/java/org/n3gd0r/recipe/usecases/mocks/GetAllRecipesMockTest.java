package org.n3gd0r.recipe.usecases.mocks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.domain.Recipe;
import org.n3gd0r.recipe.domain.RecipeMother;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.get.GetAllRecipesHandler;
import org.n3gd0r.recipe.usecase.get.GetAllRecipesParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class GetAllRecipesMockTest {
    private RecipeRepository repository;
    private GetAllRecipesHandler getAllRecipesQuery;

    @BeforeEach
    void setUp() {
        repository = mock(RecipeRepository.class);
        getAllRecipesQuery = new GetAllRecipesHandler(repository);
    }

    @Test
    void testGetAllRecipes() {
        UUID randomUserId = UUID.randomUUID();
        PageRequest page = PageRequest.of(0, 5);
        when(repository.findAll(eq(randomUserId), eq(page)))
                .thenReturn(Page.empty());
        getAllRecipesQuery.execute(new GetAllRecipesParameters(randomUserId, 0, 5));
        verify(repository, times(1)).findAll(eq(randomUserId), eq(page));
    }

    @Test
    void testGetAllRecipesPopulated() {
        UUID randomUserId = UUID.randomUUID();
        PageRequest page = PageRequest.of(0, 5);
        when(repository.findAll(eq(randomUserId), eq(page)))
                .thenReturn(getRecipesPage(PageRequest.of(0, 5)));

        List<Recipe> recipes = getAllRecipesQuery.execute(new GetAllRecipesParameters(randomUserId, 0, 5)).toList();
        verify(repository, times(1)).findAll(eq(randomUserId), eq(page));
        assertNotNull(recipes);
        assertTrue(recipes.size() == 2);
    }

    private Page<Recipe> getRecipesPage(Pageable pageable) {
        return new PageImpl<>(Arrays.asList(RecipeMother.recipe().build(), RecipeMother.recipe().build()), pageable, 2);
    }
}
