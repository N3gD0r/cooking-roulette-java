package org.n3gd0r.service;

import java.util.List;
import java.util.Optional;

import org.n3gd0r.service.models.RecipeIngredient;
import org.n3gd0r.service.models.RecipeIngredientKey;
import org.n3gd0r.service.abs.RecipeIngredientService;

public class RecipeIngredientServiceImpl implements RecipeIngredientService {

	@Override
	public Optional<RecipeIngredient> get(RecipeIngredientKey id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'get'");
	}

	@Override
	public List<RecipeIngredient> get() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'get'");
	}

	@Override
	public void create(RecipeIngredient entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'create'");
	}

	@Override
	public void upsert(RecipeIngredientKey id, RecipeIngredient entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'upsert'");
	}

	@Override
	public void delete(RecipeIngredientKey id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}
}
