package org.n3gd0r.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.service.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.n3gd0r.repository.RecipeRepository;
import org.n3gd0r.repository.entities.RecipeEntity;
import org.n3gd0r.service.abs.RecipeService;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private final RecipeRepository repository;

    @Autowired
    private final ModelMapper mapper;

    @Override
    public Optional<Recipe> get(UUID id) {
        return repository.findById(id).map(this::toDomainObject);
    }

    @Override
    public List<Recipe> get() {
        return repository.findAll().stream().map(this::toDomainObject).toList();
    }

    @Override
    public void create(Recipe entity) {
        if (entity.getId() != null) {
            throw new RuntimeException("Recipe ID must be null to create a new Recipe.");
        }
        repository.create(toEntity(entity));
    }

    @Override
    public void upsert(UUID id, Recipe entity) {
        repository.upsert(id, toEntity(entity));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private Recipe toDomainObject(RecipeEntity recipeEntity) {
        return mapper.map(recipeEntity, Recipe.class);
    }

    private RecipeEntity toEntity(Recipe recipe) {
        return mapper.map(recipe, RecipeEntity.class);
    }

}
