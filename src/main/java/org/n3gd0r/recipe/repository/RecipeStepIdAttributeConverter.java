package org.n3gd0r.recipe.repository;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.n3gd0r.recipe.domain.RecipeStepId;

import java.util.UUID;

/**
 * RecipeStepIdConverter
 */
@Converter(autoApply = true)
public class RecipeStepIdAttributeConverter implements AttributeConverter<RecipeStepId, UUID> {

    @Override
    public UUID convertToDatabaseColumn(RecipeStepId attribute) {
        return attribute.getId();
    }

    @Override
    public RecipeStepId convertToEntityAttribute(UUID dbData) {
        return new RecipeStepId(dbData);
    }
}
