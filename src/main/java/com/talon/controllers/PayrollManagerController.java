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
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        // Reading employee and payroll data
        Map<String, Employee> employees = EmployeeUtils.ReadData();
        Map<String, Float> payrolls = PayrollUtils.ReadPayrollData(); // Assuming this method returns a map of IDs to salaries

        // Creating an observable list to hold the EmployeePayroll objects
        ObservableList<Payroll> employeePayrollList = FXCollections.observableArrayList();

        // Iterating through the employees map and linking with payroll data
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            String employeeId = entry.getKey();
            Employee employee = entry.getValue();

            // Fetching the corresponding payroll for the employee
            Float salary = payrolls.getOrDefault(employeeId, 0.0f); // Default to 0.0 salary if not found

            // Creating an EmployeePayroll object (with employee name and salary) and adding it to the list
            Payroll employeePayroll = new Payroll(employee.getName(), salary);
            employeePayrollList.add(employeePayroll);
        }

        // Populating the TableView with the EmployeePayroll objects
        employeeTable.setItems(employeePayrollList);

        employeeTable.setOnMouseClicked(event -> {
            // Check if the click was on a valid row
            if (event.getClickCount() == 1 && !employeeTable.getSelectionModel().isEmpty()) {
                Payroll selectedPayroll = employeeTable.getSelectionModel().getSelectedItem();
                if (selectedPayroll != null) {
                    String selectedName = selectedPayroll.getName();
                    System.out.println("Selected Employee Name: " + selectedName);
                    // You can also perform other actions here
                }
            }
        });
    }

    

}

