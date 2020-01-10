package com.furion.pizzeria.controllers;


import com.furion.pizzeria.models.ClientOrder;
import com.furion.pizzeria.models.Item;
import com.furion.pizzeria.repositories.OrderRepository;
import com.furion.pizzeria.repositories.PizzaRepository;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


@Controller
public class CartController {

    private final PizzaRepository pizzaRepository;
    private final OrderRepository orderRepository;

    public CartController(PizzaRepository pizzaRepository, OrderRepository orderRepository) {
        this.pizzaRepository = pizzaRepository;
        this.orderRepository = orderRepository;
    }

/*    @RequestMapping(value = {"/cart/add/{id}", "cart/add/{id}"}, method = RequestMethod.GET)
    public String buy(@PathVariable("id") String id, HttpSession session) {
        System.out.println("w cartcontroller cart/add/id");

        if (session.getAttribute("cart") == null) {
            List<Item> cart = new ArrayList<Item>();
            cart.add(new Item(pizzaRepository.findById(Long.parseLong(id)).get(), 1));
            session.setAttribute("cart", cart);
        } else {
            List<Item> cart = (List<Item>) session.getAttribute("cart");
            int index = this.exists(Long.parseLong(id), cart);
            if (index == -1) {
                cart.add(new Item(pizzaRepository.findById(Long.parseLong(id)).get(), 1));
            } else {
                int quantity = cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(quantity);
            }
            session.setAttribute("cart", cart);
        }

        return "redirect:/menu";
    }
*/
    @RequestMapping(value = "/cart/add", method = RequestMethod.POST)
    public String addToCart(HttpServletRequest request, HttpSession session){
        // this way you get value of the input you want
        System.out.println("in cart controller form");
        int q = Integer.parseInt(request.getParameter("pizza_quantity"));
        Long id = Long.parseLong(request.getParameter("pizza_id"));
        String url = (String) request.getParameter("location");

        if (session.getAttribute("cart") == null) {
            List<Item> cart = new ArrayList<Item>();
            cart.add(new Item(pizzaRepository.findById(id).get(), q));
            BigDecimal price = new BigDecimal("0.0");
            price = price.add(new BigDecimal(pizzaRepository.findById(id).get().getPrice().toString()));
            price = price.multiply(new BigDecimal(Integer.toString(q)));
            session.setAttribute("cart", cart);
            session.setAttribute("price", price);
        } else {
            List<Item> cart = (List<Item>) session.getAttribute("cart");
            BigDecimal price = (BigDecimal) session.getAttribute("price");
            int index = this.exists(id, cart);
            if (index == -1) {
                cart.add(new Item(pizzaRepository.findById(id).get(), q));
            } else {
                int quantity = cart.get(index).getQuantity() + q;
                cart.get(index).setQuantity(quantity);
            }
            BigDecimal temp = new BigDecimal("0.0");
            temp = temp.add(new BigDecimal(pizzaRepository.findById(id).get().getPrice().toString()));
            temp = temp.multiply(new BigDecimal(Integer.toString(q)));
            price = price.add(temp);
            session.setAttribute("cart", cart);
            session.setAttribute("price", price);
        }

        return "redirect:"+url;
    }

    private int exists(Long id, List<Item> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getPizza().getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    @RequestMapping(value = "/cart/submit", method = RequestMethod.POST)
    public String submitCart(HttpServletRequest request, HttpSession session) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        String address = (String) request.getParameter("address");
        String url = (String) request.getParameter("location");
        System.out.println(url);
        for (Item i:cart) {
            for(int cnt = 0; cnt<i.getQuantity();cnt++) {
                ClientOrder o = new ClientOrder(
                        i.getPizza(),
                        address,
                        0,
                        Calendar.getInstance((TimeZone.getTimeZone("UTC"))),
                        false
                );
                orderRepository.save(o);
            }
        }

        return "redirect:"+url;
    }

}
