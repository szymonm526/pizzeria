package com.furion.pizzeria.repositories;

import com.furion.pizzeria.models.ClientOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<ClientOrder, Long> {
    List<ClientOrder> findAllByStan(int stan);
}
