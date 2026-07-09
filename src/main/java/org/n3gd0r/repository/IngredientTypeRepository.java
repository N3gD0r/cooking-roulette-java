package org.n3gd0r.repository;

import org.n3gd0r.repository.jparepos.IngredientTypeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class IngredientTypeRepository {
    @Autowired
    private final IngredientTypeJpaRepository jpaRepository;
}
