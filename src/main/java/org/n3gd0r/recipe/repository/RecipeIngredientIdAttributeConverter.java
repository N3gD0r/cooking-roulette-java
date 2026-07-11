package org.n3gd0r.recipe.repository;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.n3gd0r.recipe.domain.RecipeIngredientId;

import java.util.UUID;

/**
 * RecipeIngredientIdAttributeConverter
 */
@Converter(autoApply = true)
class RecipeIngredientIdAttributeConverter implements AttributeConverter<RecipeIngredientId, UUID> {

    @Override
    public UUID convertToDatabaseColumn(RecipeIngredientId attribute) {
        return attribute.getId();
    }

    @Override
    public RecipeIngredientId convertToEntityAttribute(UUID dbData) {
        return new RecipeIngredientId(dbData);
    }
}
