package com.furion.pizzeria.repositories;

import com.furion.pizzeria.models.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
