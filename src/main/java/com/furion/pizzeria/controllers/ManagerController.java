package com.furion.pizzeria.controllers;

import com.furion.pizzeria.models.ClientOrder;
import com.furion.pizzeria.models.Ingredient;
import com.furion.pizzeria.models.Item;
import com.furion.pizzeria.models.Pizza;
import com.furion.pizzeria.repositories.BudgetRepository;
import com.furion.pizzeria.repositories.IngredientRepository;
import com.furion.pizzeria.repositories.OrderRepository;
import com.furion.pizzeria.repositories.PizzaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ManagerController {

    private final BudgetRepository budgetRepository;
    private final OrderRepository orderRepository;
    private final IngredientRepository ingredientRepository;

    public ManagerController(BudgetRepository budgetRepository, OrderRepository orderRepository, IngredientRepository ingredientRepository) {
        this.budgetRepository = budgetRepository;
        this.orderRepository = orderRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("budget", budgetRepository.findBudgetByName("mainbudget"));
        return "manager/manager";
    }

    @RequestMapping(value = "/manager/historia", method = RequestMethod.GET)
    public String historia(Model model)
    {
        model.addAttribute("orders", orderRepository.findAllByStan(2));
        return "manager/historia";
    }

    @RequestMapping(value = "/manager/skladniki", method = RequestMethod.GET)
    public String skladnikiGet(Model model)
    {
        List<Ingredient> ingredients = new LinkedList<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        model.addAttribute("ingredients", ingredients);
        return "manager/skladniki";
    }

    @RequestMapping(value = "/manager/skladniki/add", method = RequestMethod.POST)
    public String skladniki(HttpServletRequest request, HttpSession session){
        // this way you get value of the input you want
        System.out.println("in cart controller form");
        int q = Integer.parseInt(request.getParameter("howMuch"));
        Long id = Long.parseLong(request.getParameter("ingredient_id"));

        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
        ingredient.setInStock(ingredient.getInStock() + q);
        ingredientRepository.save(ingredient);

        return "redirect:/manager/skladniki";
    }


}
