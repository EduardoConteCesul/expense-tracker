package com.contedev.expensetracker.repository;

import com.contedev.expensetracker.config.MongoConfig;
import com.contedev.expensetracker.model.Expense;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static com.mongodb.client.model.Sorts.descending;

public class ExpenseRepository {

    private final MongoCollection<Expense> collection =
            MongoConfig.db.getCollection("expense", Expense.class);

    public ObservableList<Expense> findAll(){
        var list = FXCollections.<Expense>observableArrayList();
        collection.find().sort(descending()).forEach(list::add);
        return list;
    }

    public void insert(Expense e){
        collection.insertOne(e);
    }

    public void delete(Expense e){
        collection.deleteOne(Filters.eq("_id", e.getId()));
    }
}
