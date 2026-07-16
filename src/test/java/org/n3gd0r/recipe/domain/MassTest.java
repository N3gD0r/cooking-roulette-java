package org.n3gd0r.recipe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;

public class MassTest {
    @Test
    void testMassCanBeCreatedWithAPositiveValue() {
        Mass mass = new Mass(1);
        assertThat(mass).isNotNull();
        assertThat(mass.value()).isGreaterThan(0);
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
        assertThat(mass).isNotNull();
        assertThat(mass.value()).isEqualTo(1000);
    }

    @Test
    void testMassFromGrams() {
        Mass mass = Mass.ofGrams(250);
        assertThat(mass).isNotNull();
        assertThat(mass.value()).isEqualTo(250);
    }
}
