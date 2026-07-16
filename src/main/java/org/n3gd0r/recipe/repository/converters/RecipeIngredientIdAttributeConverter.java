package org.n3gd0r.recipe.repository.converters;

import java.util.UUID;

import org.n3gd0r.recipe.domain.RecipeIngredientId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

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
