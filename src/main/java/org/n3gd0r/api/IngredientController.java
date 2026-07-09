package org.n3gd0r.api;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.n3gd0r.api.dtos.IngredientDTO;
import org.n3gd0r.service.abs.IngredientService;
import org.n3gd0r.service.models.Ingredient;
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
public class IngredientController {

    @Autowired
    private final IngredientService service;

    @Autowired
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<IngredientDTO> get(UUID id) {
        return service.get(id).map(this::toDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<IngredientDTO>> get() {
        return ResponseEntity.ok(service.get().stream().map(this::toDTO).toList());
    }

    @PostMapping
    public ResponseEntity<Void> postIngredient(IngredientDTO ingredientDTO) {
        if (ingredientDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        service.create(toDomainObject(ingredientDTO));
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> putIngredient(UUID id, IngredientDTO ingredientDTO) {
        if (!hasValidId(id, ingredientDTO)) {
            return ResponseEntity.badRequest().build();
        }
        service.upsert(id, toDomainObject(ingredientDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteIngredient(UUID id) {
        if (service.get(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private boolean hasValidId(UUID id, IngredientDTO ingredientDTO) {
        return ingredientDTO.getId() == null || Objects.equals(id, ingredientDTO.getId());
    }

    private IngredientDTO toDTO(Ingredient ingredient) {
        return mapper.map(ingredient, IngredientDTO.class);
    }

    private Ingredient toDomainObject(IngredientDTO ingredientDTO) {
        return mapper.map(ingredientDTO, Ingredient.class);
    }
}
