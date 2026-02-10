package com.expense.controller;

import com.expense.model.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.List;

public class ExpenseController {

    private final ObservableList<Expense> expenses =
            FXCollections.observableArrayList();

    private final File file = new File("data/expenses.txt");

    public ObservableList<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(String category, double amount) {
        expenses.add(new Expense(category, amount));
        saveToFile();
    }

    public double getTotal() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    public void loadFromFile() {
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                expenses.add(new Expense(
                        parts[0],
                        Double.parseDouble(parts[1])
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Expense e : expenses) {
                bw.write(e.getCategory() + "," + e.getAmount());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
