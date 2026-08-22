package org.n3gd0r.recipe.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RecipeIngredient {
    @Id
    private UUID id;
    // TODO: can ingredientName be unique?
    @Column(length = 255)
    private String ingredientName;
    @Enumerated(EnumType.STRING)
    private IngredientEnum ingredientType;
    private Mass weight;
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    public RecipeIngredient(UUID id, String ingredientName, IngredientEnum ingredientType, Mass weight) {
        this.id = id;
        this.ingredientName = ingredientName;
        this.ingredientType = ingredientType;
        this.weight = weight;
    }

    public int totalMass() {
        return weight.value();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RecipeIngredient other = (RecipeIngredient) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.getId()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "RecipeIngredient [id=%s, name=%s]".formatted(id.toString(), ingredientName);
    }
}
