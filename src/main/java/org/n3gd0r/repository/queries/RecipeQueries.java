package org.n3gd0r.repository.queries;

public class RecipeQueries {
    public static final String MERGE_QUERY = "MERGE INTTO recipes (id, name, cook_time, steps_id, ingredients_id) KEY(id) VALUES (?, ?, ?, ?, ?)";
}
