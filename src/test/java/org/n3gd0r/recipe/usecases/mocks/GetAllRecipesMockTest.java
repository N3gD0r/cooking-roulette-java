package org.n3gd0r.recipe.usecases.mocks;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n3gd0r.recipe.repository.RecipeRepository;
import org.n3gd0r.recipe.usecase.get.GetAllRecipesParameters;
import org.n3gd0r.recipe.usecase.get.GetAllRecipesQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class GetAllRecipesMockTest {
    private RecipeRepository repository;
    private GetAllRecipesQuery getAllRecipesQuery;

    @BeforeEach
    void setUp() {
        repository = mock(RecipeRepository.class);
        getAllRecipesQuery = new GetAllRecipesQuery(repository);
    }

    @Test
    void testGetAllRecipes() {
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(Page.empty());
        getAllRecipesQuery.execute(new GetAllRecipesParameters());
        verify(repository, times(1)).findAll(any(Pageable.class));
    }

}
