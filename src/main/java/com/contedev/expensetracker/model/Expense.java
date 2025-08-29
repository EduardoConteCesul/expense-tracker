package com.contedev.expensetracker.model;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Expense {
    // Pojo simples que representa a despesa
    private ObjectId id;
    private String description;
    private double amount;
    private LocalDate date;

    public Expense(){}

    public Expense(String description, double amount, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public ObjectId getId() {
        return id;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
