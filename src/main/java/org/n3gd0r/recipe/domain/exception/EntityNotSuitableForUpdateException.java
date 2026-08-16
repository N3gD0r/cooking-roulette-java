package org.n3gd0r.recipe.domain.exception;

/**
 * EntityNotSuitableForUpdateException
 * throws when an entity is not suitable for an update operation
 */
public class EntityNotSuitableForUpdateException extends RuntimeException {
    public EntityNotSuitableForUpdateException(String updateEntityName, String reason) {
        super("Current %s is not suitable for update operation, reason: %s".formatted(updateEntityName, reason));
    }
}
