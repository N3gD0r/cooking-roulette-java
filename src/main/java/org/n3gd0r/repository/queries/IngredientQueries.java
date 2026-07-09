package org.n3gd0r.repository.queries;

public class IngredientQueries {
    public static final String MERGE_QUERY = "MERGE INTO ingredients (id, name, type_id) KEY(ID) VALUES (?, ?, ?) ";
}
