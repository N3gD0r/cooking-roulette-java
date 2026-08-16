package org.n3gd0r.recipe.usecase.exception;

public class NothingToPatchException extends RuntimeException {
    public NothingToPatchException() {
        super("Cannot update recipe with missing fields");
    }
}
