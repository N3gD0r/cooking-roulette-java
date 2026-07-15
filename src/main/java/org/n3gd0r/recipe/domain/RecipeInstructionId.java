package org.n3gd0r.recipe.domain;

import java.util.UUID;

import org.n3gd0r.commons.AbstractEntityId;

/**
 * RecipeInstructionId
 */
public class RecipeInstructionId extends AbstractEntityId<UUID> {
    protected RecipeInstructionId() {
    }

    public RecipeInstructionId(UUID id) {
        super(id);
    }
}
