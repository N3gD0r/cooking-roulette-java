package org.n3gd0r.infrastructure.hateoas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeletedRecipeResponseModel extends RepresentationModel<DeletedRecipeResponseModel> {
    private boolean deleted;

    public static DeletedRecipeResponseModel of(boolean wasDeleted) {
        return new DeletedRecipeResponseModel(wasDeleted);
    }
}
