package org.n3gd0r.roulette.config;

import org.n3gd0r.infrastructure.mediator.Mediator;
import org.n3gd0r.roulette.usecase.GetRandomRecipe;
import org.n3gd0r.roulette.usecase.GetRandomRecipeWithFilters;
import org.n3gd0r.roulette.usecase.RandomRecipeQuery;
import org.n3gd0r.roulette.usecase.RandomRecipeWithFilters;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class MediatorRandom {
    private final Mediator mediator;
    private final GetRandomRecipe getRandomRecipe;
    private final GetRandomRecipeWithFilters getRandomRecipeWithFilters;

    public MediatorRandom(GetRandomRecipe getRandomRecipe, Mediator mediator,
            GetRandomRecipeWithFilters getRandomRecipeWithFilters) {
        this.getRandomRecipe = getRandomRecipe;
        this.mediator = mediator;
        this.getRandomRecipeWithFilters = getRandomRecipeWithFilters;
    }

    @PostConstruct
    public void registerHandlers() {
        mediator.registerHandler(RandomRecipeQuery.class, getRandomRecipe);
        mediator.registerHandler(RandomRecipeWithFilters.class, getRandomRecipeWithFilters);
    }
}
