package com.talon.controllers.departmentManager;

import com.talon.models.LeaveApplication;
import com.talon.utils.EmployeeUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.talon.models.Employee;
import com.talon.utils.SessionState;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import com.talon.utils.EmailUtils;

public class LeaveManagementPageController extends DepartmentManagerMainController {

    @FXML
    private TableView<LeaveApplicationRow> leaveApplicationsTable;
    @FXML
    private TableColumn<LeaveApplicationRow, Button> actionColumn;
    @FXML
    private TableColumn<LeaveApplicationRow, Button> notifyColumn;

    @FXML
    public void initialize() {
        loadLeaveApplications();
    }

    public void refreshLeaveApplications() {
        loadLeaveApplications();
        changeValueOfActionColumnButton();
    }

    public void loadLeaveApplications() {
        try {
            if (leaveApplicationsTable == null) {
                System.out.println("leaveApplicationsTable is null");
                return;
            }
            changeValueOfActionColumnButton();
            String currentUserId = SessionState.getInstance().getLoggedInUserId();
            Employee currentUser = EmployeeUtils.getEmployeeById(currentUserId);
            String currentDepartment = currentUser != null ? currentUser.getDepartment() : null;

            if (currentDepartment == null) {
                System.out.println("Current user's department is null");
                return;
            }

            Map<String, Employee> allEmployees = EmployeeUtils.ReadData();
            leaveApplicationsTable.getItems().clear();

            List<LeaveApplicationRow> pendingApplications = new ArrayList<>();
            List<LeaveApplicationRow> otherApplications = new ArrayList<>();

            int index = 1;
            for (Employee employee : allEmployees.values()) {
                if (employee.getDepartment() != null && employee.getDepartment().equals(currentDepartment) && employee.getLeaveApplications() != null) {
                    Map<String, LeaveApplication> uniqueApplications = new HashMap<>();
                    for (LeaveApplication application : employee.getLeaveApplications()) {
                        String key = employee.getFullName() + application.getLeaveType() + application.getStartDate() + application.getEndDate();
                        uniqueApplications.put(key, application);
                    }
                    
                    for (LeaveApplication application : uniqueApplications.values()) {
                        Button actionButton = createActionButton(application, employee);
                        Button notifyButton = createNotifyButton(application, employee);
                        LeaveApplicationRow row = new LeaveApplicationRow(
                                index++,
                                employee.getFullName(),
                                application.getLeaveType(),
                                application.getStartDate(),
                                application.getEndDate(),
                                application.getReason(),
                                application.getStatus(),
                                actionButton,
                                notifyButton);
                        
                        if (application.getStatus().equalsIgnoreCase("PENDING")) {
                            pendingApplications.add(row);
                        } else {
                            actionButton.setDisable(true);
                            otherApplications.add(row);
                        }
                    }
                }
            }

            // Add pending applications first
            leaveApplicationsTable.getItems().addAll(pendingApplications);
            // Then add other applications
            leaveApplicationsTable.getItems().addAll(otherApplications);
        } catch (Exception e) {
            e.printStackTrace();
            router.switchScene("login");
        }
    }

    private Button createActionButton(LeaveApplication application, Employee employee) {
        Button actionButton = new Button("Action");
        actionButton.setOnAction(event -> showActionDialog(application, employee));
        if (!application.getStatus().equalsIgnoreCase("PENDING")) {
            actionButton.setDisable(true);
        }
        return actionButton;
    }

    private Button createNotifyButton(LeaveApplication application, Employee employee) {
        Button notifyButton = new Button("Notify");
        notifyButton.setOnAction(event -> sendNotificationEmail(application, employee));
        if (!application.getStatus().equalsIgnoreCase("APPROVED") && !application.getStatus().equalsIgnoreCase("REJECTED")) {
            notifyButton.setDisable(true);
        }
        return notifyButton;
    }

    private void showActionDialog(LeaveApplication application, Employee employee) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Leave Application Action");
        alert.setHeaderText("Choose an action for " + employee.getFullName() + "'s leave application");
        alert.setContentText("Do you want to approve or reject this leave application?");

        ButtonType approveButton = new ButtonType("Approve");
        ButtonType rejectButton = new ButtonType("Reject");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(approveButton, rejectButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == approveButton) {
                handleApprove(application, employee);
            } else if (response == rejectButton) {
                handleReject(application, employee);
            }
        });
    }

    private void handleApprove(LeaveApplication application, Employee employee) {
        try {
            application.setStatus("APPROVED");
            updateEmployeeLeaveApplication(employee);
            SuccessAlert("Leave application approved successfully");
            loadLeaveApplications();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorAlert("Error approving leave application");
        }
    }

    private void handleReject(LeaveApplication application, Employee employee) {
        try {
            application.setStatus("REJECTED");
            updateEmployeeLeaveApplication(employee);
            SuccessAlert("Leave application rejected successfully");
            loadLeaveApplications();
        } catch (Exception e) {
            e.printStackTrace();
            ErrorAlert("Error rejecting leave application");
        }
    }

    private void updateEmployeeLeaveApplication(Employee employee) throws Exception {
        Map<String, Employee> employees = EmployeeUtils.ReadData();
        //find employee key
        String employeeKey = null;
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            // comapre by username
            if (entry.getValue().getUsername().equals(employee.getUsername())) {
                employeeKey = entry.getKey();
                break;
            }
        }
        if (employeeKey == null) {
            throw new Exception("Employee not found");
        }
        employees.put(employeeKey, employee);
        EmployeeUtils.WriteData(employees);
        refreshLeaveApplications();
    }

    private void changeValueOfActionColumnButton() {
        if (actionColumn == null) {
            System.out.println("actionColumn is null");
            return;
        }
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("actionButton"));
        actionColumn.setCellFactory(column -> new TableCell<LeaveApplicationRow, Button>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(item);
                }
            }
        });
    }

    private void sendNotificationEmail(LeaveApplication application, Employee employee) {
        try {
            String recipientEmail = employee.getEmail();
            if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
                ErrorAlert("Employee email is missing. Please contact the HR department.");
                return;
            }

            String employeeName = employee.getFullName();
            String leaveType = application.getLeaveType();
            String startDate = application.getStartDate();
            String endDate = application.getEndDate();
            boolean isApproved = application.getStatus().equalsIgnoreCase("APPROVED");

            EmailUtils.sendLeaveApplicationNotification(recipientEmail, employeeName, leaveType, startDate, endDate, isApproved);
            SuccessAlert("Notification email sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            ErrorAlert("Error sending notification email: " + e.getMessage());
        }
    }
}