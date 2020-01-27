package com.furion.pizzeria.controllers;


import com.furion.pizzeria.models.Ingredient;
import com.furion.pizzeria.models.Pizza;
import com.furion.pizzeria.repositories.IngredientRepository;
import com.furion.pizzeria.repositories.PizzaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@Controller
public class MenuController {

    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;

//    public Comparator<String> stringComparator = new Comparator<String>() {
//        @Override
//        public int compare(String s, String t1) {
//            return s.compareTo(t1);
//        }
//    };


    public MenuController(PizzaRepository pizzaRepository, IngredientRepository ingredientRepository) {
        this.pizzaRepository = pizzaRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping({"/menu","menu"})
    public String pizzas(Model model) {
        System.out.println("==== in menu controller ====");
        List<Pizza> pizzas = new LinkedList<>();
        pizzaRepository.findAll().forEach(pizzas::add);
        model.addAttribute("pizzadd", pizzas);
//        model.addAttribute("stringComparator",stringComparator);
        return "menu/menu";
    }

    @GetMapping({"/menu/error"})
    public String error(Model model)
    {
        return "/menu/error";
    }

}
