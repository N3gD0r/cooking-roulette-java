package org.n3gd0r.roulette.config;

import org.n3gd0r.infrastructure.mediator.Mediator;
import org.n3gd0r.roulette.usecase.RandomRecipeFiltersParameters;
import org.n3gd0r.roulette.usecase.RandomRecipeFiltersQuery;
import org.n3gd0r.roulette.usecase.RandomRecipeParameters;
import org.n3gd0r.roulette.usecase.RandomRecipeQuery;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class MediatorRandom {
    private final Mediator mediator;
    private final RandomRecipeQuery getRandomRecipe;
    private final RandomRecipeFiltersQuery getRandomRecipeWithFilters;

    public MediatorRandom(RandomRecipeQuery getRandomRecipe, Mediator mediator,
            RandomRecipeFiltersQuery getRandomRecipeWithFilters) {
        this.getRandomRecipe = getRandomRecipe;
        this.mediator = mediator;
        this.getRandomRecipeWithFilters = getRandomRecipeWithFilters;
    }

    @PostConstruct
    public void registerHandlers() {
        mediator.registerHandler(RandomRecipeParameters.class, getRandomRecipe);
        mediator.registerHandler(RandomRecipeFiltersParameters.class, getRandomRecipeWithFilters);
    }
}
