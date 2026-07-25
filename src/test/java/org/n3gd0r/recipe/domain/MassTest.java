package org.n3gd0r.recipe.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MassTest {
    @Test
    void testMassCanBeCreatedWithAPositiveValue() {
        Mass mass = new Mass(1);

        assertNotNull(mass);
        assertTrue(mass.value() > 0);
    }

    @Test
    void testMassCannotBeCreatedWithANegativeValue() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Mass(-1))
                .withMessage("The mass value should be a positive number");
    }

    @Test
    void testMassFromKilograms() {
        Mass mass = Mass.ofKilograms(1);

        assertNotNull(mass);
        assertEquals(mass.value(), 1000);
    }

    @Test
    void testMassFromGrams() {
        Mass mass = Mass.ofGrams(250);

        assertNotNull(mass);
        assertEquals(mass.value(), 250);
    }
}
