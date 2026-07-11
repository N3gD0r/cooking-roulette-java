package org.n3gd0r.recipe.repository;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.n3gd0r.recipe.domain.RecipeId;

import java.util.UUID;

/**
 * RecipeIdConverter
 */
@Converter
class RecipeIdAttributeConverter implements AttributeConverter<RecipeId, UUID> {

    @Override
    public UUID convertToDatabaseColumn(RecipeId attribute) {
        return attribute.getId();
    }

    @Override
    public RecipeId convertToEntityAttribute(UUID dbData) {
        return new RecipeId(dbData);
    }
}
