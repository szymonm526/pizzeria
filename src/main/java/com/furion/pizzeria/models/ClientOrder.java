package com.furion.pizzeria.models;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class ClientOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    private Pizza pizza = new Pizza();

    private String address;
    private int stan;

    private Calendar utilCalendar;

    private  boolean eatIn;//??

    public ClientOrder()
    {

    }

    public ClientOrder(Pizza pizza, String address, int stan, Calendar utilCalendar, boolean eatIn) {

        this.pizza = pizza;
        this.address = address;
        this.stan = stan;
        this.utilCalendar = utilCalendar;
        this.eatIn = eatIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStan() {
        return stan;
    }

    public void setStan(int stan) {
        this.stan = stan;
    }

    public boolean isEatIn() {
        return eatIn;
    }

    public void setEatIn(boolean eatIn) {
        this.eatIn = eatIn;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Calendar getUtilCalendar() {
        return utilCalendar;
    }

    public void setUtilCalendar(Calendar utilCalendar) {
        this.utilCalendar = utilCalendar;
    }
}
