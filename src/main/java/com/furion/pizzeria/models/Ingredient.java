package com.furion.pizzeria.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Long inStock;

    //@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    //private Set<IngredientMapping> ingredientMapping = new HashSet<>();

    public Ingredient() {
    }

    public Ingredient(String description, Long inStock) {
        this.description = description;
        this.inStock = inStock;
    }

 /*   public Set<IngredientMapping> getIngredientMapping() {
        return ingredientMapping;
    }

    public void setIngredientMapping(Set<IngredientMapping> ingredientMapping) {
        this.ingredientMapping = ingredientMapping;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getInStock() {
        return inStock;
    }

    public void setInStock(Long inStock) {
        this.inStock = inStock;
    }
}
