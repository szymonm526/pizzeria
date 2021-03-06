package com.furion.pizzeria.bootstrap;

import com.furion.pizzeria.models.Budget;
import com.furion.pizzeria.models.Ingredient;
import com.furion.pizzeria.models.IngredientMapping;
import com.furion.pizzeria.models.Pizza;
import com.furion.pizzeria.repositories.BudgetRepository;
import com.furion.pizzeria.repositories.IngredientRepository;
import com.furion.pizzeria.repositories.PizzaRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;


@Component
public class PizzaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private PizzaRepository pizzaRepository;
    private IngredientRepository ingredientRepository;
    private BudgetRepository budgetRepository;

    public PizzaBootstrap(PizzaRepository pizzaRepository, IngredientRepository ingredientRepository, BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
        this.pizzaRepository = pizzaRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {

        Ingredient ananas = new Ingredient("ananas",1000L);
        Ingredient kielbasa = new Ingredient("kiełbasa",1000L);
        Ingredient sosPomidorowy = new Ingredient("sos pomidorowy", 1000L);
        Ingredient mozzarella = new Ingredient("mozzarella", 1000L);
        Ingredient peperoni = new Ingredient("peperoni", 1000L);
        Ingredient pieczarki = new Ingredient("pieczarki", 1000L);
        Ingredient salame = new Ingredient("salame", 1000L);
        Ingredient ser = new Ingredient("ser", 1000L);
        Ingredient szynka = new Ingredient("szynka",1000L);
        Ingredient papryka = new Ingredient("papryka",1000L);
        Ingredient ciasto = new Ingredient("ciasto", 5L);

        ingredientRepository.save(papryka);
        ingredientRepository.save(ananas);
        ingredientRepository.save(kielbasa);
        ingredientRepository.save(sosPomidorowy);
        ingredientRepository.save(mozzarella);
        ingredientRepository.save(peperoni);
        ingredientRepository.save(pieczarki);
        ingredientRepository.save(salame);
        ingredientRepository.save(ser);
        ingredientRepository.save(szynka);
        ingredientRepository.save(ciasto);


        Pizza pizzaPepperoni = new Pizza("Pepperoni", "[opis Pepperoni]", new BigDecimal("15.0"),
                new IngredientMapping(ciasto,1L),
                new IngredientMapping(sosPomidorowy, 10L),
                new IngredientMapping(mozzarella,10L),
                new IngredientMapping(kielbasa, 10L),
                new IngredientMapping(peperoni,20L)
        );

        Pizza pizzaCapricciosa = new Pizza("Capricciosa", "[opis Capricciosa]",new BigDecimal("20.0"),
                new IngredientMapping(ciasto,1L),
                new IngredientMapping(sosPomidorowy, 10L),
                new IngredientMapping(mozzarella,10L),
                new IngredientMapping(szynka, 10L),
                new IngredientMapping(pieczarki,10L)
        );

        Pizza pizzaHawajska = new Pizza("Hawajska", "[opis Hawajska]",new BigDecimal("25.0"),
                new IngredientMapping(ciasto,1L),
                new IngredientMapping(sosPomidorowy, 10L),
                new IngredientMapping(mozzarella,10L),
                new IngredientMapping(szynka, 10L),
                new IngredientMapping(ananas,10L)
        );

        Pizza pizzaMoja = new Pizza("Salame", "[opis Salame]",new BigDecimal("20.0"),
                new IngredientMapping(ciasto,1L),
                new IngredientMapping(sosPomidorowy,10L),
                new IngredientMapping(szynka, 10L),
                new IngredientMapping(papryka,10L),
                new IngredientMapping(ser,10L)
        );



        pizzaRepository.save(pizzaPepperoni);
        pizzaRepository.save(pizzaCapricciosa);
        pizzaRepository.save(pizzaHawajska);
        pizzaRepository.save(pizzaMoja);



        Budget mainBudget = new Budget("mainbudget", new BigDecimal("2000.0"));
        budgetRepository.save(mainBudget);


        // pizzaRepository.save(new Pizza("Salame","dobra je", new IngredientMapping(ingredientA,100L), new IngredientMapping(ingredientB,500L)));
       // bookRepository.save(new Book("Book 1", new BookPublisher(publisherA, new Date()), new BookPublisher(publisherB, new Date())));
        //bookRepository.save(new Book("Book 2", new BookPublisher(publisherA, new Date())));

    }
}
