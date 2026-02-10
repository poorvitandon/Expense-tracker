package com.expense;

import com.expense.controller.ExpenseController;
import com.expense.model.Expense;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ExpenseApp extends Application {

    private final ExpenseController controller = new ExpenseController();
    private final Label totalLabel = new Label("Total: ₹0.0");

    @Override
    public void start(Stage stage) {

        controller.loadFromFile();

        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        Button addButton = new Button("Add Expense");

        TableView<Expense> table = new TableView<>();
        table.setItems(controller.getExpenses());

        TableColumn<Expense, String> categoryCol =
                new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getCategory()));

        TableColumn<Expense, Number> amountCol =
                new TableColumn<>("Amount");
        amountCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty(
                        data.getValue().getAmount()));

        table.getColumns().addAll(categoryCol, amountCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        addButton.setOnAction(e -> {
            try {
                String category = categoryField.getText();
                double amount = Double.parseDouble(amountField.getText());

                controller.addExpense(category, amount);
                updateTotal();

                categoryField.clear();
                amountField.clear();
            } catch (Exception ex) {
                showAlert("Invalid Input", "Please enter valid values.");
            }
        });

        VBox inputBox = new VBox(10,
                categoryField, amountField, addButton, totalLabel);
        inputBox.setPadding(new Insets(15));
        inputBox.setPrefWidth(200);

        BorderPane root = new BorderPane();
        root.setLeft(inputBox);
        root.setCenter(table);

        Scene scene = new Scene(root, 600, 350);
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm());

        stage.setTitle("Smart Expense Tracker");
        stage.setScene(scene);
        stage.show();

        updateTotal();
    }

    private void updateTotal() {
        totalLabel.setText("Total: ₹" + controller.getTotal());
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
