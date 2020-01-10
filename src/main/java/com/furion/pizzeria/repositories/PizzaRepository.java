package com.furion.pizzeria.repositories;

import com.furion.pizzeria.models.Pizza;
import org.springframework.data.repository.CrudRepository;

public interface PizzaRepository extends CrudRepository<Pizza, Long> {
}
