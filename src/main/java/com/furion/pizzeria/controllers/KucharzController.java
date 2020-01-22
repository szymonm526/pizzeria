package com.furion.pizzeria.controllers;


import com.furion.pizzeria.models.*;
import com.furion.pizzeria.repositories.IngredientRepository;
import com.furion.pizzeria.repositories.OrderRepository;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class KucharzController {

    private final OrderRepository orderRepository;
    private final IngredientRepository ingredientRepository;

    public KucharzController(OrderRepository orderRepository, IngredientRepository ingredientRepository) {
        this.orderRepository = orderRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @RequestMapping(value = "/kucharz", method = RequestMethod.GET)
    public String index(Model model)
    {
        model.addAttribute("orders", orderRepository.findAllByStan(0));
        return "kucharz/kucharz";
    }

    @RequestMapping(value = "/kucharz/update", method = RequestMethod.POST)
    public String updateOrder(HttpServletRequest request){
        Long id = Long.parseLong(request.getParameter("order_id"));
        ClientOrder orderToUpdate = orderRepository.findById(id).orElse(null);
        if(orderToUpdate.isEatIn())
            orderToUpdate.setStan(1);
        else
            orderToUpdate.setStan(2);

        Pizza pizza = orderToUpdate.getPizza();
        Set<IngredientMapping> ingredientMapping = pizza.getIngredientMappings();

        for(IngredientMapping im : ingredientMapping)
        {

            Ingredient ingredient = ingredientRepository.findById(im.getIngredient().getId()).orElse(null);
            long howMany = im.getHowMany();
            long howManyInStock = ingredient.getInStock();

            if(howMany <= howManyInStock)
                ingredient.setInStock(ingredient.getInStock() - howMany);
            else
                return "kucharz/fail";

        }

        for(IngredientMapping im : ingredientMapping) {
            ingredientRepository.save(im.getIngredient());
        }

        orderRepository.save(orderToUpdate);
        return "redirect:/kucharz";
    }
}
