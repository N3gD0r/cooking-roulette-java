package org.n3gd0r.recipe.repository;

import java.util.UUID;

import org.n3gd0r.recipe.domain.RecipeId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * RecipeIdConverter
 */
@Converter
class RecipeIdAttributeConverter implements AttributeConverter<RecipeId, UUID>{

	@Override
	public UUID convertToDatabaseColumn(RecipeId attribute) {
        return attribute.getId();
	}

	@Override
	public RecipeId convertToEntityAttribute(UUID dbData) {
        return new RecipeId(dbData);
	}
}
