package org.n3gd0r.recipe.repository;

import org.n3gd0r.recipe.domain.Mass;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * MassAttributeConverter
 */
@Converter(autoApply = true)
class MassAttributeConverter implements AttributeConverter<Mass, Float> {

    @Override
    public Float convertToDatabaseColumn(Mass attribute) {
        return attribute.value();
    }

    @Override
    public Mass convertToEntityAttribute(Float dbData) {
        return Mass.ofGrams(dbData);
    }
}
