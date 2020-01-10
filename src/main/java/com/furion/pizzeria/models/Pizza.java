package com.furion.pizzeria.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Pizza  implements Comparable<Pizza> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;


    @JsonManagedReference
    @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL)
    private Set<IngredientMapping> ingredientMappings = new HashSet<>();

    public Pizza()
    {

    }

    public Pizza(String name, String description, BigDecimal price, IngredientMapping... ingredientMappings) {
        this.name = name;
        this.description = description;
        for(IngredientMapping ingredientMapping: ingredientMappings) ingredientMapping.setPizza(this);
        this.ingredientMappings = Stream.of(ingredientMappings).collect(Collectors.toSet());
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<IngredientMapping> getIngredientMappings() {
        return ingredientMappings;
    }

    public void setIngredientMappings(Set<IngredientMapping> ingredientMappings) {
        this.ingredientMappings = ingredientMappings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Pizza pizza) {
        return this.name.compareTo(pizza.name);
    }
}
