package org.n3gd0r.recipe.repository;

import java.util.UUID;

import org.n3gd0r.recipe.domain.RecipeInstructionId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * RecipeInstructionIdAttributeConverter
 */
@Converter(autoApply = true)
public class RecipeInstructionIdAttributeConverter implements AttributeConverter<RecipeInstructionId, UUID> {

    @Override
    public UUID convertToDatabaseColumn(RecipeInstructionId attribute) {
        return attribute.getId();
    }

    @Override
    public RecipeInstructionId convertToEntityAttribute(UUID dbData) {
        return new RecipeInstructionId(dbData);
    }
}
