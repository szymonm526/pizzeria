package com.furion.pizzeria.controllers;

import com.furion.pizzeria.models.Pizza;
import com.furion.pizzeria.repositories.PizzaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedList;
import java.util.List;

@Controller
public class KelnerController {

    private PizzaRepository pizzaRepository;

    public KelnerController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }


    @RequestMapping(value = "/kelner", method = RequestMethod.GET)
    public String index() {
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
}
