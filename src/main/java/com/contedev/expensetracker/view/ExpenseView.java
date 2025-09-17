package com.contedev.expensetracker.view;

import com.contedev.expensetracker.model.Expense;
import com.contedev.expensetracker.view_model.ExpenseViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.NumberStringConverter;

import java.time.LocalDate;

// "Controller" vinculado ao FXML
// Faz APENAS a cola entre componente da tela e a viewModel
// Aqui nao se implementa regra de negocio
public class ExpenseView {

    @FXML private TextField description;
    @FXML private TextField amountField;
    @FXML private DatePicker datePicker;
    @FXML private Button addButton;
    @FXML private TableView<Expense> expenseTable;
    @FXML private TableColumn<Expense, LocalDate> dateCol;
    @FXML private TableColumn<Expense, String> descCol;
    @FXML private TableColumn<Expense, Number> amountCol;
    @FXML private Button deleteButton;
    @FXML private Label totalLabel;

    private final ExpenseViewModel vm = new ExpenseViewModel();

    // Metodo chamado automaticamento pelo FXMLLoader
    @FXML
    private void initialize(){

        
        description.textProperty().bindBidirectional(vm.descriptionProperty());

        TextFormatter<Number> amountFormatter = new TextFormatter<>(new NumberStringConverter());
        amountField.setTextFormatter(amountFormatter);
        // Fazer um binding bidirecional entre o NumberProperty(VM) e o valueProperty(formatter)
        Bindings.bindBidirectional(vm.amountProperty(), amountFormatter.valueProperty());

        // Dizer qual getter do expense abastece cada coluna
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        // Popular os dados
        expenseTable.setItems(vm.getExpenses());

        totalLabel.textProperty().bind(vm.totalProperty().asString("Total: R$ %.2f"));

        addButton.setOnAction(expenseTable -> vm.addExpense());
        deleteButton.setOnAction(expense -> vm.deleteSelected(
                expenseTable.getSelectionModel().getSelectedItem()
        ));


    }
}
