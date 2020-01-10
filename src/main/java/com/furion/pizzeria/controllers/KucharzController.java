package com.furion.pizzeria.controllers;


import com.furion.pizzeria.models.ClientOrder;
import com.furion.pizzeria.models.Item;
import com.furion.pizzeria.repositories.OrderRepository;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class KucharzController {

    private final OrderRepository orderRepository;

    public KucharzController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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
        orderToUpdate.setStan(1);
        orderRepository.save(orderToUpdate);
        return "redirect:/kucharz";
    }
}
