package com.talon.controllers.payrollOfficer;

import com.talon.controllers.BaseController;
import com.talon.models.Employee;
import com.talon.models.PayrollTransaction;
import com.talon.utils.PayrollTransactionUtils;
import com.talon.utils.EmployeeUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import java.util.HashMap;
import javafx.beans.property.SimpleObjectProperty;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.scene.layout.HBox;
import com.talon.utils.PayslipGenerator;
import javafx.stage.FileChooser;
import java.io.File;

public class PayrollOfficerMainController extends BaseController {
    @FXML TableView<PayrollTransaction> payrollTransactionTable;
    @FXML private ComboBox<String> monthComboBox;
    @FXML private ComboBox<String> yearComboBox;
    @FXML private Button resetFilterButton;

    private List<PayrollTransaction> allTransactions;

    @FXML
    public void initialize() {
        if (payrollTransactionTable != null) {
            setupPayrollTable();
        } else {
            System.err.println("payrollTransactionTable is null in initialize()");
        }
        if (monthComboBox != null && yearComboBox != null) {
            setupComboBoxes();
        } else {
            System.err.println("monthComboBox or yearComboBox is null in setupComboBoxes()");
        }
        loadAllTransactions();
    }

    public void loadAllTransactions() {
        try {
            Map<String, PayrollTransaction> transactions = PayrollTransactionUtils.ReadData();
            allTransactions = new ArrayList<>(transactions.values());
            allTransactions.sort((t1, t2) -> LocalDate.parse(t2.getTransactionDate()).compareTo(LocalDate.parse(t1.getTransactionDate())));
            payrollTransactionTable.getItems().setAll(allTransactions);
        } catch (Exception e) {
            router.switchScene("login");
        }
    }

    @FXML
    private void handleLoadTransactions() {
        String selectedMonth = monthComboBox.getValue();
        String selectedYear = yearComboBox.getValue();

        if (selectedMonth == null && selectedYear == null) {
            payrollTransactionTable.getItems().setAll(allTransactions);
            return;
        }

        List<PayrollTransaction> filteredTransactions = allTransactions.stream()
            .filter(transaction -> {
                LocalDate transactionDate = LocalDate.parse(transaction.getTransactionDate());
                boolean monthMatch = selectedMonth == null || transactionDate.getMonth().toString().equalsIgnoreCase(selectedMonth);
                boolean yearMatch = selectedYear == null || transactionDate.getYear() == Integer.parseInt(selectedYear);
                return monthMatch && yearMatch;
            })
            .collect(Collectors.toList());

        payrollTransactionTable.getItems().setAll(filteredTransactions);
    }

    @FXML
    private void handleResetFilter() {
        monthComboBox.setValue(null);
        yearComboBox.setValue(null);
        payrollTransactionTable.getItems().setAll(allTransactions);
    }

    @SuppressWarnings("unchecked")
    private void setupPayrollTable() {
        TableColumn<PayrollTransaction, String> employeeIdCol = new TableColumn<>("Employee ID");
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        TableColumn<PayrollTransaction, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(cellData -> {
            String employeeId = cellData.getValue().getEmployeeId();
            Employee employee = null;
            try {
                employee = EmployeeUtils.getEmployeeById(employeeId);
            } catch (Exception e) {
                e.printStackTrace();
                ErrorAlert("Error retrieving employee data: " + e.getMessage());
                return new SimpleStringProperty("");
            }
            if (employee == null) {
                return new SimpleStringProperty("");
            }
            return new SimpleStringProperty(employee.getUsername());
        });

        TableColumn<PayrollTransaction, String> fullNameCol = new TableColumn<>("Full Name");
        fullNameCol.setCellValueFactory(cellData -> {
            String employeeId = cellData.getValue().getEmployeeId();
            Employee employee = null;
            try {
                employee = EmployeeUtils.getEmployeeById(employeeId);
            } catch (Exception e) {
                e.printStackTrace();
                ErrorAlert("Error retrieving employee data: " + e.getMessage());
                return new SimpleStringProperty("");
            }
            if (employee == null) {
                return new SimpleStringProperty("");
            }
            return new SimpleStringProperty(employee.getFullName());
        });

        TableColumn<PayrollTransaction, String> dateCol = new TableColumn<>("Transaction Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));

        TableColumn<PayrollTransaction, Double> grossCol = new TableColumn<>("Gross Salary");
        grossCol.setCellValueFactory(new PropertyValueFactory<>("grossSalary"));

        TableColumn<PayrollTransaction, Double> employeeEpfCol = new TableColumn<>("Employee EPF");
        employeeEpfCol.setCellValueFactory(new PropertyValueFactory<>("employeeEpf"));

        TableColumn<PayrollTransaction, Double> employeeSocsoCol = new TableColumn<>("Employee SOCSO");
        employeeSocsoCol.setCellValueFactory(new PropertyValueFactory<>("employeeSocso"));

        TableColumn<PayrollTransaction, Double> employeeEisCol = new TableColumn<>("Employee EIS");
        employeeEisCol.setCellValueFactory(new PropertyValueFactory<>("employeeEis"));

        TableColumn<PayrollTransaction, Double> employeeTaxCol = new TableColumn<>("Employee Tax");
        employeeTaxCol.setCellValueFactory(new PropertyValueFactory<>("employeeTax"));

        TableColumn<PayrollTransaction, Double> employerEpfCol = new TableColumn<>("Employer EPF");
        employerEpfCol.setCellValueFactory(new PropertyValueFactory<>("employerEpf"));

        TableColumn<PayrollTransaction, Double> employerSocsoCol = new TableColumn<>("Employer SOCSO");
        employerSocsoCol.setCellValueFactory(new PropertyValueFactory<>("employerSocso"));

        TableColumn<PayrollTransaction, Double> employerEisCol = new TableColumn<>("Employer EIS");
        employerEisCol.setCellValueFactory(new PropertyValueFactory<>("employerEis"));

        TableColumn<PayrollTransaction, Double> totalEmployerContributionCol = new TableColumn<>("Total Employer Contribution");
        totalEmployerContributionCol.setCellValueFactory(cellData -> {
            PayrollTransaction transaction = cellData.getValue();
            double totalContribution = transaction.getGrossSalary() +
                                       transaction.getEmployerEpf() +
                                       transaction.getEmployerSocso() +
                                       transaction.getEmployerEis();
            return new SimpleObjectProperty<>(totalContribution);
        });

        TableColumn<PayrollTransaction, Double> pcbCol = new TableColumn<>("PCB");
        pcbCol.setCellValueFactory(new PropertyValueFactory<>("pcb"));

        TableColumn<PayrollTransaction, Double> latePenaltyCol = new TableColumn<>("Late Penalty");
        latePenaltyCol.setCellValueFactory(new PropertyValueFactory<>("latePenalty"));

        TableColumn<PayrollTransaction, Double> netCol = new TableColumn<>("Net Salary");
        netCol.setCellValueFactory(new PropertyValueFactory<>("netSalary"));

        TableColumn<PayrollTransaction, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(column -> {
            return new TableCell<PayrollTransaction, Void>() {
                private final Button editButton = new Button("Edit");
                private final Button deleteButton = new Button("Delete");
                private final HBox buttonBox = new HBox(5, editButton, deleteButton);

                {
                    editButton.setOnAction(event -> {
                        PayrollTransaction transaction = getTableView().getItems().get(getIndex());
                        handleEditTransaction(transaction);
                    });

                    deleteButton.setOnAction(event -> {
                        PayrollTransaction transaction = getTableView().getItems().get(getIndex());
                        handleDeleteTransaction(transaction);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(buttonBox);
                    }
                }
            };
        });

        TableColumn<PayrollTransaction, Void> downloadCol = new TableColumn<>("Download");
        downloadCol.setCellFactory(column -> {
            return new TableCell<PayrollTransaction, Void>() {
                private final Button downloadButton = new Button("Download Payslip");

                {
                    downloadButton.setOnAction(event -> {
                        PayrollTransaction transaction = getTableView().getItems().get(getIndex());
                        handleDownloadPayslip(transaction);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(downloadButton);
                    }
                }
            };
        });

        payrollTransactionTable.getColumns().addAll(employeeIdCol, usernameCol, fullNameCol, dateCol, grossCol, 
                                                    employeeEpfCol, employeeSocsoCol, employeeEisCol, employeeTaxCol,
                                                    employerEpfCol, employerSocsoCol, employerEisCol,
                                                    totalEmployerContributionCol,
                                                    pcbCol, latePenaltyCol, netCol, actionsCol, downloadCol);
    }

    private void setupComboBoxes() {
        if (monthComboBox != null) {
            monthComboBox.getItems().addAll("January", "February", "March", "April", "May", "June", 
                                           "July", "August", "September", "October", "November", "December");
            monthComboBox.setOnAction(e -> handleLoadTransactions());
        } else {
            System.err.println("monthComboBox is null in setupComboBoxes()");
        }
        
        if (yearComboBox != null) {
            int currentYear = LocalDate.now().getYear();
            for (int i = currentYear - 20; i <= currentYear + 20; i++) {
                yearComboBox.getItems().add(String.valueOf(i));
            }
            yearComboBox.setOnAction(e -> handleLoadTransactions());
        } else {
            System.err.println("yearComboBox is null in setupComboBoxes()");
        }

        if (resetFilterButton != null) {
            resetFilterButton.setOnAction(e -> handleResetFilter());
        } else {
            System.err.println("resetFilterButton is null in setupComboBoxes()");
        }
    }

    @FXML
    private void handleCreateNewTransaction() {
        Dialog<PayrollTransaction> dialog = new Dialog<>();
        dialog.setTitle("Create New Payroll Transaction");
        dialog.setHeaderText(null);

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField employeeIdField = new TextField();
        DatePicker transactionDatePicker = new DatePicker();

        grid.add(new Label("Employee ID:"), 0, 0);
        grid.add(employeeIdField, 1, 0);
        grid.add(new Label("Transaction Date:"), 0, 1);
        grid.add(transactionDatePicker, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                try {
                    String employeeId = employeeIdField.getText();
                    LocalDate transactionDate = transactionDatePicker.getValue();
                    String transactionDateString = transactionDate.toString();

                    Employee employee = EmployeeUtils.getEmployeeById(employeeId);
                    if (employee == null) {
                        ErrorAlert("Employee not found");
                        return null;
                    }

                    double grossSalary = employee.getGrossSalary() != null ? employee.getGrossSalary() : 0;
                    boolean isLateThreeTimes = EmployeeUtils.isLateThreeTimes(employeeId);
                    double latePenalty = isLateThreeTimes ? 100 : 0;

                    PayrollTransaction newTransaction = new PayrollTransaction(employeeId, transactionDateString, grossSalary);
                    newTransaction.setLatePenalty(latePenalty);
                    return newTransaction;
                } catch (Exception e) {
                    ErrorAlert("Error creating payroll transaction: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(newTransaction -> {
            try {
                Map<String, PayrollTransaction> transactions;
                try {
                    transactions = PayrollTransactionUtils.ReadData();
                } catch (Exception e) {
                    // If reading fails, initialize an empty map
                    transactions = new HashMap<>();
                }
                String transactionId = generateTransactionId(newTransaction);
                transactions.put(transactionId, newTransaction);
                PayrollTransactionUtils.WriteData(transactions);
                loadAllTransactions(); // Refresh the table
                SuccessAlert("New payroll transaction created successfully.");
            } catch (Exception e) {
                ErrorAlert("Error creating new payroll transaction: " + e.getMessage());
            }
        });
    }

    private String generateTransactionId(PayrollTransaction transaction) {
        return transaction.getEmployeeId() + "_" + transaction.getTransactionDate().toString();
    }

    private void handleEditTransaction(PayrollTransaction transaction) {
        Dialog<PayrollTransaction> dialog = new Dialog<>();
        dialog.setTitle("Edit Payroll Transaction");
        dialog.setHeaderText(null);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField employeeIdField = new TextField(transaction.getEmployeeId());
        DatePicker transactionDatePicker = new DatePicker(LocalDate.parse(transaction.getTransactionDate()));
        TextField grossSalaryField = new TextField(String.valueOf(transaction.getGrossSalary()));
        TextField employeeEpfField = new TextField(String.valueOf(transaction.getEmployeeEpf()));
        TextField employeeSocsoField = new TextField(String.valueOf(transaction.getEmployeeSocso()));
        TextField employeeEisField = new TextField(String.valueOf(transaction.getEmployeeEis()));
        TextField employeeTaxField = new TextField(String.valueOf(transaction.getEmployeeTax()));
        TextField employerEpfField = new TextField(String.valueOf(transaction.getEmployerEpf()));
        TextField employerSocsoField = new TextField(String.valueOf(transaction.getEmployerSocso()));
        TextField employerEisField = new TextField(String.valueOf(transaction.getEmployerEis()));
        TextField pcbField = new TextField(String.valueOf(transaction.getPcb()));
        TextField latePenaltyField = new TextField(String.valueOf(transaction.getLatePenalty()));

        grid.add(new Label("Employee ID:"), 0, 0);
        grid.add(employeeIdField, 1, 0);
        grid.add(new Label("Transaction Date:"), 0, 1);
        grid.add(transactionDatePicker, 1, 1);
        grid.add(new Label("Gross Salary:"), 0, 2);
        grid.add(grossSalaryField, 1, 2);
        grid.add(new Label("Employee EPF:"), 0, 3);
        grid.add(employeeEpfField, 1, 3);
        grid.add(new Label("Employee SOCSO:"), 0, 4);
        grid.add(employeeSocsoField, 1, 4);
        grid.add(new Label("Employee EIS:"), 0, 5);
        grid.add(employeeEisField, 1, 5);
        grid.add(new Label("Employee Tax:"), 0, 6);
        grid.add(employeeTaxField, 1, 6);
        grid.add(new Label("Employer EPF:"), 0, 7);
        grid.add(employerEpfField, 1, 7);
        grid.add(new Label("Employer SOCSO:"), 0, 8);
        grid.add(employerSocsoField, 1, 8);
        grid.add(new Label("Employer EIS:"), 0, 9);
        grid.add(employerEisField, 1, 9);
        grid.add(new Label("PCB:"), 0, 10);
        grid.add(pcbField, 1, 10);
        grid.add(new Label("Late Penalty:"), 0, 11);
        grid.add(latePenaltyField, 1, 11);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    transaction.setEmployeeId(employeeIdField.getText());
                    transaction.setTransactionDate(transactionDatePicker.getValue().toString());
                    transaction.setGrossSalary(Double.parseDouble(grossSalaryField.getText()));
                    transaction.setEmployeeEpf(Double.parseDouble(employeeEpfField.getText()));
                    transaction.setEmployeeSocso(Double.parseDouble(employeeSocsoField.getText()));
                    transaction.setEmployeeEis(Double.parseDouble(employeeEisField.getText()));
                    transaction.setEmployeeTax(Double.parseDouble(employeeTaxField.getText()));
                    transaction.setEmployerEpf(Double.parseDouble(employerEpfField.getText()));
                    transaction.setEmployerSocso(Double.parseDouble(employerSocsoField.getText()));
                    transaction.setEmployerEis(Double.parseDouble(employerEisField.getText()));
                    transaction.setPcb(Double.parseDouble(pcbField.getText()));
                    transaction.setLatePenalty(Double.parseDouble(latePenaltyField.getText()));

                    // Calculate net salary
                    double netSalary = transaction.getGrossSalary() -
                                       transaction.getEmployeeEpf() -
                                       transaction.getEmployeeSocso() -
                                       transaction.getEmployeeEis() -
                                       transaction.getPcb() -
                                       transaction.getLatePenalty();
                    transaction.setNetSalary(netSalary);

                    return transaction;
                } catch (NumberFormatException e) {
                    ErrorAlert("Invalid input. Please enter valid numbers.");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(updatedTransaction -> {
            try {
                Map<String, PayrollTransaction> transactions = PayrollTransactionUtils.ReadData();
                String transactionId = generateTransactionId(updatedTransaction);
                transactions.put(transactionId, updatedTransaction);
                PayrollTransactionUtils.WriteData(transactions);
                loadAllTransactions(); // Refresh the table
                SuccessAlert("Payroll transaction updated successfully.");
            } catch (Exception e) {
                ErrorAlert("Error updating payroll transaction: " + e.getMessage());
            }
        });
    }

    private void handleDeleteTransaction(PayrollTransaction transaction) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Payroll Transaction");
        alert.setHeaderText("Are you sure you want to delete this payroll transaction?");
        alert.setContentText("This action cannot be undone.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Map<String, PayrollTransaction> transactions = PayrollTransactionUtils.ReadData();
                    String transactionId = generateTransactionId(transaction);
                    transactions.remove(transactionId);
                    PayrollTransactionUtils.WriteData(transactions);
                    loadAllTransactions(); // Refresh the table
                    SuccessAlert("Payroll transaction deleted successfully.");
                } catch (Exception e) {
                    ErrorAlert("Error deleting payroll transaction: " + e.getMessage());
                }
            }
        });
    }

    private void handleDownloadPayslip(PayrollTransaction transaction) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Payslip");
            fileChooser.setInitialFileName("payslip_" + transaction.getEmployeeId() + "_" + transaction.getTransactionDate() + ".pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            
            File file = fileChooser.showSaveDialog(payrollTransactionTable.getScene().getWindow());
            
            if (file != null) {
                String outputPath = file.getAbsolutePath();
                PayslipGenerator.generatePayslip(transaction, outputPath);
                SuccessAlert("Payslip generated successfully. Saved as: " + outputPath);
            }
        } catch (Exception e) {
            ErrorAlert("Error generating payslip: " + e.getMessage());
        }
    }
}