package com.talon.controllers.common;

import com.talon.controllers.BaseController;
import com.talon.models.Employee;
import com.talon.models.LeaveApplication;
import com.talon.utils.EmployeeUtils;
import com.talon.utils.SessionState;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import java.time.LocalDate;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class ApplyLeavePageController extends BaseController {

    @FXML
    private TableView<LeaveApplicationRow> leaveApplicationsTable;

    @FXML
    private TableColumn<LeaveApplicationRow, Button> deleteColumn;

    private Employee currentEmployee;

    @FXML
    public void initialize() {
        if (!isUserLoggedIn()) {
            router.switchScene("login");
            return;
        }
        loadEmployeeData();
        loadLeaveApplications();
    }

    public void loadEmployeeData() {
        Employee employee = loadLeaveApplications();
        if (employee == null) {
            router.switchScene("login");
            return;
        }
        currentEmployee = employee;
    }

    public Employee loadLeaveApplications() {
        try {
            String employeeId = SessionState.getInstance().getLoggedInUserId();
            if (employeeId == null || employeeId.isEmpty()) {
                ErrorAlert("No user is currently logged in.");
                return null;
            }
            Employee employee = EmployeeUtils.getEmployeeById(employeeId);
            if (employee == null) {
                ErrorAlert("Employee data not found.");
                return null;
            }
            
            leaveApplicationsTable.getItems().clear(); // Clear existing items
            
            if (employee.getLeaveApplications() != null) {
                for (int i = 0; i < employee.getLeaveApplications().size(); i++) {
                    LeaveApplication leave = employee.getLeaveApplications().get(i);
                    final int index = i;  // Create a final variable to capture the index
                    LeaveApplicationRow row = new LeaveApplicationRow(index, leave.getLeaveType(), leave.getStartDate(), leave.getEndDate(), leave.getReason(), leave.getStatus(), leave);
                    
                    Button deleteButton = row.getDeleteButton();
                    deleteButton.setOnAction(event -> handleDeleteLeaveApplication(leave, index));
                    if (leave.getStatus().equalsIgnoreCase("APPROVED")) {
                        deleteButton.setDisable(true);
                    }
                    
                    leaveApplicationsTable.getItems().add(row);
                }
                changeValueOfDeleteColumn();
            }
            return employee;
        } catch (Exception e) {
            e.printStackTrace();
            ErrorAlert("Error loading leave applications");
            return null;
        }
    }

    @FXML
    private void handleApplyNewLeave() {
        if (!isUserLoggedIn()) {
            ErrorAlert("You must be logged in to apply for leave.");
            return;
        }
        Dialog<LeaveApplication> dialog = new Dialog<>();
        dialog.setTitle("Apply New Leave");
        dialog.setHeaderText(null);

        ButtonType applyButtonType = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        ComboBox<String> leaveTypeComboBox = new ComboBox<>();
        leaveTypeComboBox.getItems().addAll("Annual Leave", "Medical Leave", "Maternity Leave", "Unpaid Leave");
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        TextField reasonField = new TextField();

        grid.add(new Label("Leave Type:"), 0, 0);
        grid.add(leaveTypeComboBox, 1, 0);
        grid.add(new Label("Start Date:"), 0, 1);
        grid.add(startDatePicker, 1, 1);
        grid.add(new Label("End Date:"), 0, 2);
        grid.add(endDatePicker, 1, 2);
        grid.add(new Label("Reason:"), 0, 3);
        grid.add(reasonField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyButtonType) {
                try {
                    String leaveType = leaveTypeComboBox.getValue();
                    LocalDate startDate = startDatePicker.getValue();
                    LocalDate endDate = endDatePicker.getValue();
                    String reason = reasonField.getText();

                    if (leaveType == null || startDate == null || endDate == null || reason.isEmpty()) {
                        ErrorAlert("Please fill in all fields.");
                        return null;
                    }

                    if (endDate.isBefore(startDate)) {
                        ErrorAlert("End date cannot be before start date.");
                        return null;
                    }

                    int leaveDays = endDate.getDayOfYear() - startDate.getDayOfYear() + 1;
                    int entitledDays = getEntitledDays(leaveType);
                    int takenDays = getTakenDays(leaveType);

                    if (leaveDays > (entitledDays - takenDays)) {
                        ErrorAlert("You don't have enough leave days.");
                        return null;
                    }

                    return new LeaveApplication(leaveType, startDate.toString(), endDate.toString(), reason, "PENDING");
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorAlert("An error occurred while processing your leave application.");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(leaveApplication -> {
            if (leaveApplication != null) {
                try {
                    // if currentEmployee.getLeaveApplications() is null, initialize it
                    if (currentEmployee.getLeaveApplications() == null) {
                        currentEmployee.setLeaveApplications(new ArrayList<>());
                    }
                    currentEmployee.getLeaveApplications().add(leaveApplication);
                    Map<String, Employee> employees = EmployeeUtils.ReadData();
                    employees.put(SessionState.getInstance().getLoggedInUserId(), currentEmployee);
                    EmployeeUtils.WriteData(employees);
                    loadLeaveApplications();
                    SuccessAlert("Leave application submitted successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorAlert("Error submitting leave application.");
                }
            }
        });
    }

    private void changeValueOfDeleteColumn() {
        if (deleteColumn != null) {
            deleteColumn.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
            deleteColumn.setCellFactory(column -> new TableCell<LeaveApplicationRow, Button>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        LeaveApplicationRow row = getTableView().getItems().get(getIndex());
                        setGraphic(row.getDeleteButton());
                    }
                }
            });
        }
    }

    private void handleDeleteLeaveApplication(LeaveApplication leaveApplication, int index) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Leave Application");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this leave application?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Map<String, Employee> employees = EmployeeUtils.ReadData();
                    Employee updatedEmployee = employees.get(SessionState.getInstance().getLoggedInUserId());
                    List<LeaveApplication> leaveApplications = updatedEmployee.getLeaveApplications();
                    
                    if (index >= 0 && index < leaveApplications.size()) {
                        leaveApplications.remove(index);
                        updatedEmployee.setLeaveApplications(leaveApplications);
                        employees.put(SessionState.getInstance().getLoggedInUserId(), updatedEmployee);
                        EmployeeUtils.WriteData(employees);
                        
                        // Refresh the table data
                        loadEmployeeData();
                        loadLeaveApplications();
                        
                        SuccessAlert("Leave application deleted successfully.");
                    } else {
                        ErrorAlert("Leave application not found.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorAlert("Error deleting leave application.");
                }
            }
        });
    }

    private int getEntitledDays(String leaveType) throws Exception {
        int yearsOfService = EmployeeUtils.getTotalServiceYears(SessionState.getInstance().getLoggedInUserId());
        switch (leaveType) {
            case "Annual Leave":
                return EmployeeUtils.getAnnualLeaveEntitlement(yearsOfService);
            case "Medical Leave":
                return EmployeeUtils.getMedicalLeaveEntitlement(yearsOfService);
            case "Maternity Leave":
                return EmployeeUtils.getMaternityLeaveEntitlement(yearsOfService, currentEmployee.getGender());
            case "Unpaid Leave":
                return EmployeeUtils.getUnpaidLeaveEntitlement();
            default:
                return 0;
        }
    }

    private int getTakenDays(String leaveType) {
        String employeeId = SessionState.getInstance().getLoggedInUserId();
        try {
            Employee employee = EmployeeUtils.getEmployeeById(employeeId);
            if (employee == null) {
                ErrorAlert("Employee data not found.");
                return 0;
            }
            // if employee.getLeaveApplications() is null, initialize it
            if (employee.getLeaveApplications() == null) {
                employee.setLeaveApplications(new ArrayList<>());
            }
            return (int) employee.getLeaveApplications().stream()
                    .filter(leave -> leave.getLeaveType().equalsIgnoreCase(leaveType)
                            && leave.getStatus().equalsIgnoreCase("APPROVED")
                            && LocalDate.parse(leave.getStartDate()).getYear() == LocalDate.now().getYear())
                    .count();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorAlert("Error getting taken days");
            return 0;
        }
    }

    private boolean isUserLoggedIn() {
        String loggedInUserId = SessionState.getInstance().getLoggedInUserId();
        return loggedInUserId != null && !loggedInUserId.isEmpty();
    }
}
