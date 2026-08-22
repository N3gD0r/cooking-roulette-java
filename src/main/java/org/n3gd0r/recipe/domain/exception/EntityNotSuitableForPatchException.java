package org.n3gd0r.recipe.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EntityNotSuitableForUpdateException
 * throws when an entity is not suitable for an update operation
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityNotSuitableForPatchException extends RuntimeException {
    public EntityNotSuitableForPatchException(String updateEntityName, String reason) {
        super("Current %s is not suitable for update operation, reason: %s".formatted(updateEntityName, reason));
    }
}
