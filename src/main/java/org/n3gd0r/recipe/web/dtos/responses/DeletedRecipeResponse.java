package org.n3gd0r.recipe.web.dtos.responses;

public record DeletedRecipeResponse(boolean deleted) {
    public static DeletedRecipeResponse of(boolean wasDeleted) {
        return new DeletedRecipeResponse(wasDeleted);
    }
}
