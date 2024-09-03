package com.talon.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.talon.Router;
import com.talon.models.Employee;
import com.talon.models.Payroll;
import com.talon.models.SessionState;
import com.talon.utils.EmployeeUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PayrollManagerController extends EmployeeController {
    private final Router route = Router.getInstance();
    private Employee selectedEmployee; // Store the selected Payroll object

    //components for view employee list
    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> departmentColumn;

    @FXML
    private TableColumn<Employee, String> roleColumn;

    @FXML
    private TableColumn<Employee, String> positionColumn;

    //components for view all payrolls
    @FXML
    private TableView<Payroll> payrollTable;

    @FXML
    private TableColumn<Payroll, String> usernameColumn;

    @FXML
    private TableColumn<Payroll, String> grossColumn;

    @FXML
    private TableColumn<Payroll, String> netColumn;

    @FXML
    private TableColumn<Payroll, String> epfColumn;

    @FXML
    private TableColumn<Payroll, String> socsoColumn;

    @FXML
    private TableColumn<Payroll, String> pcbColumn;

    @FXML
    private void switchToSelectEmployeePayroll() {
        System.out.println(EmployeeUtils.ReadData());
        route.switchToScene("selectEmployeePayroll");
    }

    @FXML
    private void switchToPayrollList() {
        route.switchToScene("payrollList");
    }

    @FXML
    private void switchToViewPayroll() {
        route.switchToScene("viewPayroll");
    }

    @Override
    public void updateUI() {
        if (route.getCurrentSceneName().equals("selectEmployeePayroll")) {
            loadEmployeeData();
        }if (route.getCurrentSceneName().equals("payrollList")) {
            loadPayrollData();
        }
    }

    private void loadEmployeeData() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        // Reading employee and payroll data
        Map<String, Employee> employees = EmployeeUtils.ReadData();

        // Creating an observable list to hold the EmployeePayroll objects
        ObservableList<Employee> employeePayrollList = FXCollections.observableArrayList();

        // Iterating through the employees map and linking with payroll data
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            Employee employee = entry.getValue();

            employeePayrollList.add(employee);
        }

        // Populating the TableView with the EmployeePayroll objects
        employeeTable.setItems(employeePayrollList);

        employeeTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && !employeeTable.getSelectionModel().isEmpty()) {
                selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
                System.out.println("Selected Employee Name: " + selectedEmployee.getUsername());
            }
        });

    }

    @FXML
    private void logOut() throws Exception {
        SessionState.getInstance().setEmployee(null);
        route.switchToScene("LoginPage");
    }

    @FXML
    private void onCreateButtonClick() {
        if (selectedEmployee != null) {
            try {
                System.out.println(String.format("Created payroll for %s", selectedEmployee.getUsername()));
                EmployeeUtils.addPayrollRecord(selectedEmployee.getUsername(), new Payroll(3000f, LocalDate.now()));
            } catch (Exception ex) {
                System.out.println("Error saving payroll: " + ex.getMessage());
            }
        } else {
            System.out.println("No employee selected");
        }
    }

    private void loadPayrollData() {
        // Set up the columns in the TableView
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        grossColumn.setCellValueFactory(new PropertyValueFactory<>("grossSalary"));
        netColumn.setCellValueFactory(new PropertyValueFactory<>("netSalary"));
        epfColumn.setCellValueFactory(new PropertyValueFactory<>("epfEmployee"));
        socsoColumn.setCellValueFactory(new PropertyValueFactory<>("socsoEmployee"));
        pcbColumn.setCellValueFactory(new PropertyValueFactory<>("pcb"));
    
        try {
            // Retrieve all employee data
            Map<String, Employee> employees = EmployeeUtils.ReadData();
    
            // Creating an observable list to hold the Payroll objects
            ObservableList<Payroll> payrollList = FXCollections.observableArrayList();
    
            // Iterate through the employees and their payroll records
            for (Employee employee : employees.values()) {
                String username = employee.getUsername();
                List<Payroll> payrolls = employee.getPayrollRecords();
    
                // Add each payroll record to the list and associate it with the username
                if (payrolls != null) {
                    for (Payroll payroll : payrolls) {
                        payroll.setUsername(username); // Make sure Payroll has a setUsername method if needed
                        payrollList.add(payroll);
                    }
                }
            }
    
            // Populate the TableView with the Payroll objects
            payrollTable.setItems(payrollList);
    
        } catch (Exception e) {
            System.out.println("Error loading payroll data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

}
