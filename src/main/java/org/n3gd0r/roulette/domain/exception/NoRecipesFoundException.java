package org.n3gd0r.roulette.domain.exception;

public class NoRecipesFoundException extends RuntimeException {
    public NoRecipesFoundException() {
        super("No recipes found");
    }
}
