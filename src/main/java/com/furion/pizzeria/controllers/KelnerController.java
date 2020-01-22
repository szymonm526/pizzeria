package com.furion.pizzeria.controllers;

import com.furion.pizzeria.models.ClientOrder;
import com.furion.pizzeria.models.Ingredient;
import com.furion.pizzeria.models.IngredientMapping;
import com.furion.pizzeria.models.Pizza;
import com.furion.pizzeria.repositories.OrderRepository;
import com.furion.pizzeria.repositories.PizzaRepository;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
public class KelnerController {

    private final PizzaRepository pizzaRepository;
    private final OrderRepository orderRepository;

    public KelnerController(PizzaRepository pizzaRepository, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.pizzaRepository = pizzaRepository;
    }


    @RequestMapping(value = "/kelner", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("orders", orderRepository.findAllByStan(1));
        return "kelner/kelner";
    }

    @RequestMapping(value = "/kelner/zamowienie", method = RequestMethod.GET)
    public String zamowienia(Model model) {
        System.out.println("==== in menu controller ====");
        List<Pizza> pizzas = new LinkedList<>();
        pizzaRepository.findAll().forEach(pizzas::add);
        model.addAttribute("pizzadd", pizzas);
//        model.addAttribute("stringComparator",stringComparator);
        return "kelner/zamowienie";
    }

    @RequestMapping(value = "/kelner/update", method = RequestMethod.POST)
    public String updateOrder(HttpServletRequest request){
        Long id = Long.parseLong(request.getParameter("order_id"));
        ClientOrder orderToUpdate = orderRepository.findById(id).orElse(null);
        orderToUpdate.setStan(2);
        orderRepository.save(orderToUpdate);
        return "redirect:/kelner";
    }
}
