package org.n3gd0r.recipe.domain;

import java.util.Optional;

import org.springframework.util.Assert;

/**
 * Mass
 */
public record Mass(float value, Optional<Integer> quantity) {
    public Mass {
        Assert.isTrue(value > 0.0, "The mass value shuold be a positive number");
    }

    public static Mass ofGrams(float value) {
        return new Mass(value, Optional.empty());
    }

    public static Mass ofGrams(float value, Integer quantity) {
        return new Mass(value, Optional.of(quantity));
    }

    public static Mass ofKilograms(float value) {
        return new Mass(value, Optional.empty());
    }

    public static Mass ofKilograms(float value, Integer quantity) {
        return new Mass(value * 1000, Optional.of(quantity));
    }
}

