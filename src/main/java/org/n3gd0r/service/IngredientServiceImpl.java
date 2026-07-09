package org.n3gd0r.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.service.models.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.n3gd0r.repository.IngredientRepository;
import org.n3gd0r.repository.entities.IngredientEntity;
import org.n3gd0r.service.abs.IngredientService;

@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private final IngredientRepository repository;

    @Autowired
    private final ModelMapper mapper;

    @Override
    public Optional<Ingredient> get(UUID id) {
        return repository.findById(id).map(this::toDomainObject);
    }

    @Override
    public List<Ingredient> get() {
        return repository.findAll().stream().map(this::toDomainObject).toList();
    }

    @Override
    public void create(Ingredient entity) {
        repository.create(toEntity(entity));
    }

    @Override
    public void upsert(UUID id, Ingredient entity) {
        repository.upsert(id, toEntity(entity));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    private Ingredient toDomainObject(IngredientEntity entity) {
        return mapper.map(entity, Ingredient.class);
    }

    private IngredientEntity toEntity(Ingredient entity) {
        return mapper.map(entity, IngredientEntity.class);
    }
}
