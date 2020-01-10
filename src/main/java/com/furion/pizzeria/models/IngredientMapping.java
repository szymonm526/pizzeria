package com.furion.pizzeria.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class IngredientMapping implements Serializable {


    @JsonBackReference
    @Id
    @ManyToOne
    @JoinColumn
    private Pizza pizza;


    @Id
    @ManyToOne
    @JoinColumn
    private Ingredient ingredient;

    private Long howMany;

    public IngredientMapping() {
    }

    public IngredientMapping(Ingredient ingredient, Long howMany) {
        this.ingredient = ingredient;
        this.howMany = howMany;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Long getHowMany() {
        return howMany;
    }

    public void setHowMany(Long howMany) {
        this.howMany = howMany;
    }
}
