package org.n3gd0r.infrastructure.hateoas;

import java.util.UUID;

import static org.springframework.hateoas.IanaLinkRelations.*;

public final class RecipeLinkBuilder {
    private RecipeLinkBuilder() {
    }

    public static org.springframework.hateoas.Link selfLink(UUID id) {
        return org.springframework.hateoas.Link
                .of("/api/recipes/" + id)
                .withSelfRel();
    }

    public static org.springframework.hateoas.Link collectionLink() {
        return org.springframework.hateoas.Link
                .of("/api/recipes")
                .withRel(COLLECTION.value());
    }

    public static org.springframework.hateoas.Link searchLink() {
        return org.springframework.hateoas.Link
                .of("/api/recipes/filter")
                .withRel("search");
    }

    public static org.springframework.hateoas.Link randomLink() {
        return org.springframework.hateoas.Link
                .of("/api/random/recipe")
                .withRel("random");
    }
}
