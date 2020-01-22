package com.furion.pizzeria.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    @GetMapping({"/", ""})
    public String getIndex()
    {
        return "index";
    }

    @GetMapping({"/onas", "onas"})
    public String getONas()
    {
        return "onas";
    }
}
