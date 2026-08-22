package org.n3gd0r.recipe.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
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
public class RecipeInstruction {
    @Id
    private UUID id;
    private String instruction;
    private int instructionNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    public RecipeInstruction(UUID id, int instructionNumber, String instruction) {
        this.id = id;
        this.instructionNumber = instructionNumber;
        this.instruction = instruction;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RecipeInstruction other = (RecipeInstruction) obj;
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
        return "RecipeInstruction [id=%s]".formatted(id.toString());
    }
}
