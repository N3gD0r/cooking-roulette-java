package org.n3gd0r.recipe.repository;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.n3gd0r.recipe.domain.Mass;

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
