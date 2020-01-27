package com.furion.pizzeria.controllers;


import com.furion.pizzeria.models.*;
import com.furion.pizzeria.repositories.BudgetRepository;
import com.furion.pizzeria.repositories.OrderRepository;
import com.furion.pizzeria.repositories.PizzaRepository;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;


@Controller
public class CartController {

    private final PizzaRepository pizzaRepository;
    private final OrderRepository orderRepository;
    private final BudgetRepository budgetRepository;

    public CartController(PizzaRepository pizzaRepository, OrderRepository orderRepository, BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
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
        String url = (String) request.getParameter("location");
        try {
        int q = Integer.parseInt(request.getParameter("pizza_quantity"));
        Long id = Long.parseLong(request.getParameter("pizza_id"));

            if (q > 0) {
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
            else {
                return "redirect:" + url + "/error";
            }
        }catch (Exception e)
        {
            return "redirect:" + url + "/error";
        }

    }

    private int exists(Long id, List<Item> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getPizza().getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    @RequestMapping(value = "/cart/submitClient", method = RequestMethod.POST)
    public String submitCartClient(HttpServletRequest request, HttpSession session) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        BigDecimal price = (BigDecimal) session.getAttribute("price"); //glupi pomysl
        String address = (String) request.getParameter("address");
       // String url = (String) request.getParameter("location");
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

        Budget mainBudget =  budgetRepository.findBudgetByName("mainbudget");
        mainBudget.setMoney(mainBudget.getMoney().add(price));
        budgetRepository.save(mainBudget);
        return "sukces";
        //return "redirect:/suckes";
    }

    @RequestMapping(value = "/cart/submitKelner", method = RequestMethod.POST)//powtorzenie
    public String submitCartKelner(HttpServletRequest request, HttpSession session) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        BigDecimal price = (BigDecimal) session.getAttribute("price"); //glupi pomysl
        String address = (String) request.getParameter("address");
        //String url = (String) request.getParameter("location");
        for (Item i:cart) {
            for(int cnt = 0; cnt<i.getQuantity();cnt++) {
                ClientOrder o = new ClientOrder(
                        i.getPizza(),
                        address,
                        0,
                        Calendar.getInstance((TimeZone.getTimeZone("UTC"))),
                        true
                );



                orderRepository.save(o);
            }
        }

        Budget mainBudget =  budgetRepository.findBudgetByName("mainbudget");
        mainBudget.setMoney(mainBudget.getMoney().add(price));
        budgetRepository.save(mainBudget);
        return "kelner/sukces";
        //return "redirect:/kelner/sukces";
    }

    @RequestMapping(value="/cart/delete" , method=RequestMethod.POST)
    @ResponseBody
    public void process(@RequestParam("pizzaId") Integer id, HttpSession session) {
       // System.out.println(id);
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        BigDecimal price = (BigDecimal) session.getAttribute("price"); //glupi pomysl

        for (Item i:cart) {
            if(i.getPizza().getId() == (long)id)
            {

                BigDecimal toCut = i.getPizza().getPrice().multiply(new BigDecimal(i.getQuantity()));
                price = price.subtract(toCut);
                cart.remove(i);
                break;
            }
        }
        if(!cart.isEmpty())
            session.setAttribute("cart", cart);
        session.setAttribute("price",price);

    }

}
