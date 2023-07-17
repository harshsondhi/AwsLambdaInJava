package com.sondhi.harsh.api.dto;

public class Customer {
    public int id;
    public String firstName;

    public String lastName;
    public int reward;

    public Customer() {
    }

    public Customer(int id, String firstName, String lastName, int reward) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.reward = reward;
    }
}
