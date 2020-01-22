package com.furion.pizzeria.repositories;


import com.furion.pizzeria.models.Budget;
import org.springframework.data.repository.CrudRepository;

public interface BudgetRepository extends CrudRepository<Budget, Long> {
    Budget findBudgetByName(String name);
}
