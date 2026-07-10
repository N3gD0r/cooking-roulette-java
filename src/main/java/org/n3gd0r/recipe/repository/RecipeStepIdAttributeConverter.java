package org.n3gd0r.recipe.repository;

import java.util.UUID;

import org.n3gd0r.recipe.domain.RecipeStepId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

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
