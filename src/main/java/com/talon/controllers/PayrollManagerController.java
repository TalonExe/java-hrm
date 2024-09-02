package com.talon.controllers;

import java.util.Map;

import com.talon.Router;
import com.talon.models.Employee;
import com.talon.models.Payroll;
import com.talon.utils.EmployeeUtils;
import com.talon.utils.PayrollUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PayrollManagerController extends EmployeeController{
    private final Router route = Router.getInstance();
    private Payroll selectedPayroll; // Store the selected Payroll object

    @FXML
    private TableView<Payroll> employeeTable;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, Double> salaryColumn;

    @FXML
    private void switchToSelectEmployeePayroll(){
        System.out.println(EmployeeUtils.ReadData());
        route.switchToScene("selectEmployeePayroll");
    }

    @FXML
    private void switchToPayrollList(){
        route.switchToScene("payrollList");
    }

    @FXML
    private void switchToViewPayroll(){
        route.switchToScene("viewPayroll");
    }

    @Override
    public void updateUI() {
        if (route.getCurrentSceneName().equals("selectEmployeePayroll")) {
            loadEmployeeData();
        }
    }

    private void loadEmployeeData() {
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("grossSalary"));

        // Reading employee and payroll data
        Map<String, Employee> employees = EmployeeUtils.ReadData();

        // Creating an observable list to hold the EmployeePayroll objects
        ObservableList<Payroll> employeePayrollList = FXCollections.observableArrayList();

        // Iterating through the employees map and linking with payroll data
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            String employeeId = entry.getKey();
            Employee employee = entry.getValue();

            Payroll employeePayroll = new Payroll(employee.getUsername(), PayrollUtils.findPayrollByID(employeeId).getGrossSalary());
            employeePayrollList.add(employeePayroll);
        }

        // Populating the TableView with the EmployeePayroll objects
        employeeTable.setItems(employeePayrollList);

        employeeTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && !employeeTable.getSelectionModel().isEmpty()) {
                selectedPayroll = employeeTable.getSelectionModel().getSelectedItem();
                System.out.println("Selected Employee Name: " + selectedPayroll.getName());
            }
        });

    }

    @FXML
    private void onCreateButtonClick() {
        if (selectedPayroll != null) {
            // Assuming there's a TextField for inputting the new salary
            try {
                
            } catch (Exception ex) {
                System.out.println("Error saving payroll: " + ex.getMessage());
            }
        } else {
            System.out.println("No employee selected");
        }
    }

}

