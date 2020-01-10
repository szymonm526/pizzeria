package com.furion.pizzeria.models;


    public class Item {

        private Pizza pizza;
        private int quantity;

        public Pizza getPizza() {
            return pizza;
        }

        public void setPizza(Pizza pizza) {
            this.pizza = pizza;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Item() {
        }

        public Item(Pizza product, int quantity) {
            this.pizza = product;
            this.quantity = quantity;
        }

    }


