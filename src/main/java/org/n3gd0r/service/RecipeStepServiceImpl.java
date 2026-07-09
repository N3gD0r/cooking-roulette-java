package org.n3gd0r.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.n3gd0r.service.models.RecipeStep;
import org.n3gd0r.service.abs.RecipeStepService;

public class RecipeStepServiceImpl implements RecipeStepService {

	@Override
	public Optional<RecipeStep> get(UUID id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'get'");
	}

	@Override
	public List<RecipeStep> get() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'get'");
	}

	@Override
	public void create(RecipeStep entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'create'");
	}

	@Override
	public void upsert(UUID id, RecipeStep entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'upsert'");
	}

	@Override
	public void delete(UUID id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}

}
