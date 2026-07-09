package org.n3gd0r.api.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private UUID id;
    private String name;
    private int cookTime;
    private UUID ingredientsId;
    private UUID stepsId;
}
