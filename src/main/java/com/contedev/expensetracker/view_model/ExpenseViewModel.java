package com.contedev.expensetracker.view_model;

import com.contedev.expensetracker.model.Expense;
import com.contedev.expensetracker.repository.ExpenseRepository;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.application.Platform;

import java.time.LocalDate;

// Esta é a camada VIEWMODEL
// Não conhece as classes de UI (TextField, Botoes...)
// Ponte entre a lógica de dominio e a interface
// Mantemos só as "Propriedades" -> a view fará binding nelas
// 0 menções de controles JavaFX
// Podera ser testado em JUnit sem testes de UI
public class ExpenseViewModel{
    /*
        1º setar as propriedades que vai 'amarrar' nos componentes de entrada
    */

    // TextField de descrição
    private final StringProperty description = new SimpleStringProperty();
    private final DoubleProperty amount = new SimpleDoubleProperty();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(LocalDate.now());

    // Coleção obeservavel que abastecerá a tableview
    private final ObservableList<Expense> expenses = FXCollections.observableArrayList();

    private final DoubleBinding total = new DoubleBinding() {
        {bind(expenses);}
        @Override
        protected double computeValue() {
            return expenses.stream().mapToDouble(Expense::getAmount).sum();
        }
    };

    private final ExpenseRepository repository = new ExpenseRepository();

    // construtor - carregar os dados e configurar os observadores
    public ExpenseViewModel(){
        expenses.addAll(repository.findAll());

        expenses.addListener((ListChangeListener<? super Expense>) change -> {
            while (change.next()){
                if (change.wasAdded()){
                    change.getAddedSubList().forEach(repository::insert);
                }

                if (change.wasRemoved()){
                    change.getRemoved().forEach(repository::delete);
                }
            }
        });
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public ObjectProperty<LocalDate> getDate() {
        return date;
    }

    public ObservableList<Expense> getExpenses() {
        return expenses;
    }
    public DoubleBinding totalProperty() {
        return total;
    }

    // Cria uma expense e adiciona a lista
    public void addExpense(){
        // Regras de validação
        if (description.get() == null || description.get().isBlank()) return;
        Expense expense = new Expense(description.get(), amount.get(), date.get());
        expenses.add(expense);
        clearInputs();
    }

    private void clearInputs(){
        Platform.runLater(() -> {
            description.set("");
            amount.set(0);
            date.set(LocalDate.now());
        });
    }

    public void deleteSelected(Expense expense){
        if (expense != null) expenses.remove(expense);
    }
}
