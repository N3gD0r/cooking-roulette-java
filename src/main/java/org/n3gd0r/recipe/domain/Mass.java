package org.n3gd0r.recipe.domain;

import org.springframework.util.Assert;

/**
 * Mass
 */
public record Mass(int value) {
    public Mass {
        Assert.isTrue(value > 0, "The mass value shuold be a positive number");
    }

    public static Mass ofGrams(int value) {
        return new Mass(value);
    }

    public static Mass ofKilograms(int value) {
        return new Mass(value * 1000);
    }
}
