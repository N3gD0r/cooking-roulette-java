package org.n3gd0r.api;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.n3gd0r.api.dtos.RecipeDTO;
import org.n3gd0r.service.abs.RecipeService;
import org.n3gd0r.service.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RecipeController {
    @Autowired
    private final RecipeService service;
    @Autowired
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<RecipeDTO> getRecipe(UUID id) {
        return service.get(id).map(this::toDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getRecipes() {
        return ResponseEntity.ok(service.get().stream().map(this::toDTO).toList());
    }

    @PostMapping
    public ResponseEntity<Void> postRecipe(RecipeDTO recipeDTO) {
        if (recipeDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        service.create(toDomainObject(recipeDTO));
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> putRecipe(UUID id, RecipeDTO recipeDTO) {
        if (!hasValidId(id, recipeDTO)) {
            return ResponseEntity.badRequest().build();
        }

        service.upsert(id, toDomainObject(recipeDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRecipe(UUID id) {
        if (service.get(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private boolean hasValidId(UUID id, RecipeDTO recipeDTO) {
        return recipeDTO.getId() == null || Objects.equals(id, recipeDTO.getId());
    }

    private RecipeDTO toDTO(Recipe recipe) {
        return mapper.map(recipe, RecipeDTO.class);
    }

    private Recipe toDomainObject(RecipeDTO recipeDTO) {
        return mapper.map(recipeDTO, Recipe.class);
    }

}
