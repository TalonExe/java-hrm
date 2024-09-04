package com.talon.controllers.hr;

import com.talon.models.Employee;
import com.talon.utils.EmployeeUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.util.Map;
import javafx.scene.layout.GridPane;
import com.talon.models.ExternalWorkExperience;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.talon.models.InternalWorkExperience;

public class ViewEmploymentDetailsPageController extends HRMainController {

    @FXML private TextField fullNameLabel;
    @FXML private TextField usernameLabel;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private TextField positionLabel;
    @FXML private TextField departmentLabel;
    @FXML private TextField grossSalaryLabel;
    @FXML private Button editButton;
    @FXML private ToggleButton employmentDetailsToggle;
    @FXML private ToggleButton leaveEntitlementToggle;
    @FXML private ToggleButton externalWorkExperienceToggle;
    @FXML private ToggleButton internalWorkExperienceToggle;
    @FXML private GridPane employmentDetailsGrid;
    @FXML private GridPane leaveEntitlementGrid;
    @FXML private GridPane externalWorkExperienceGrid;
    @FXML private GridPane internalWorkExperienceGrid;
    @FXML private TextField annualLeaveEntitled;
    @FXML private TextField annualLeaveTaken;
    @FXML private TextField medicalLeaveEntitled;
    @FXML private TextField medicalLeaveTaken;
    @FXML private TextField maternityLeaveEntitled;
    @FXML private TextField maternityLeaveTaken;
    @FXML private TextField unpaidLeaveEntitled;
    @FXML private TextField unpaidLeaveTaken;

    private String employeeId;
    private boolean isEditing = false;

    @FXML
    public void initData(String employeeId) {
        this.employeeId = employeeId;
        loadEmployeeData();
        
        // Set up toggle group for the toggle buttons
        ToggleGroup toggleGroup = new ToggleGroup();
        employmentDetailsToggle.setToggleGroup(toggleGroup);
        leaveEntitlementToggle.setToggleGroup(toggleGroup);
        externalWorkExperienceToggle.setToggleGroup(toggleGroup);
        internalWorkExperienceToggle.setToggleGroup(toggleGroup);
        
        // Add listener to the toggle group
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                oldValue.setSelected(true);
            } else {
                handleToggle();
            }
        });
    }

    private void loadEmployeeData() {
        try {
            Employee employee = EmployeeUtils.getEmployeeById(employeeId);
            if (employee != null) {
                fullNameLabel.setText(employee.getFullName());
                usernameLabel.setText(employee.getUsername());
                roleComboBox.setValue(employee.getRole());
                positionLabel.setText(employee.getPosition());
                departmentLabel.setText(employee.getDepartment());
                grossSalaryLabel.setText(String.valueOf(employee.getGrossSalary()));

                setupRoleComboBox(employee.getRole());
                loadLeaveEntitlementData();

                // Set initial state
                employmentDetailsToggle.setSelected(true);
                leaveEntitlementToggle.setSelected(false);
                externalWorkExperienceToggle.setSelected(false);
                internalWorkExperienceToggle.setSelected(false);
                employmentDetailsGrid.setVisible(true);
                employmentDetailsGrid.setManaged(true);
                leaveEntitlementGrid.setVisible(false);
                leaveEntitlementGrid.setManaged(false);
                externalWorkExperienceGrid.setVisible(false);
                externalWorkExperienceGrid.setManaged(false);
                internalWorkExperienceGrid.setVisible(false);
                internalWorkExperienceGrid.setManaged(false);
                editButton.setVisible(true);
            } else {
                System.err.println("Employee not found for ID: " + employeeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorAlert("Error loading employee data");
        }
    }

    private void setupRoleComboBox(String currentRole) {
        roleComboBox.setItems(FXCollections.observableArrayList(
            "Normal Employee", "Payroll Officer", "HR Officer", "Department Manager"
        ));
        if ("System Administrator".equals(currentRole)) {
            roleComboBox.getItems().add("System Administrator");
        }
        roleComboBox.setValue(currentRole);
        roleComboBox.setDisable(true);
        roleComboBox.setStyle("-fx-text-fill: white;");
    }

    @FXML
    private void handleEdit() {
        if (!isEditing) {
            startEditing();
        } else {
            saveChanges();
        }
    }

    private void startEditing() {
        isEditing = true;
        editButton.setText("Save");
        setFieldsEditable(true);
        
        // Disable roleComboBox if the role is System Administrator
        if ("System Administrator".equals(roleComboBox.getValue())) {
            roleComboBox.setDisable(true);
        }
    }

    private void saveChanges() {
        try {
            Employee employee = EmployeeUtils.getEmployeeById(employeeId);
            if (employee != null) {
                employee.setFullName(fullNameLabel.getText());
                employee.setPosition(positionLabel.getText());
                employee.setDepartment(departmentLabel.getText());
                
                String salaryText = grossSalaryLabel.getText();
                if (salaryText != null && !salaryText.isEmpty()) {
                    try {
                        int salary = Integer.parseInt(salaryText);
                        employee.setGrossSalary(salary);
                    } catch (NumberFormatException e) {
                        ErrorAlert("Invalid salary format. Please enter a valid number.");
                        return;
                    }
                } else {
                    ErrorAlert("Salary cannot be empty.");
                    return;
                }

                if (!employee.getRole().equals("System Administrator")) {
                    employee.setRole(roleComboBox.getValue());
                }
                Map<String, Employee> employees = EmployeeUtils.ReadData();
                employees.put(employeeId, employee);
                EmployeeUtils.WriteData(employees);
                isEditing = false;
                editButton.setText("Edit");
                setFieldsEditable(false);
                SuccessAlert("Changes saved successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorAlert("Error saving changes");
        }
    }

    private void setFieldsEditable(boolean editable) {
        fullNameLabel.setEditable(editable);
        positionLabel.setEditable(editable);
        departmentLabel.setEditable(editable);
        grossSalaryLabel.setEditable(editable);
        
        // Only enable roleComboBox if editable is true and the role is not System Administrator
        roleComboBox.setDisable(!editable || "System Administrator".equals(roleComboBox.getValue()));
    }
    
    @FXML
    private void handleToggle() {
        boolean showEmploymentDetails = employmentDetailsToggle.isSelected();
        boolean showLeaveEntitlement = leaveEntitlementToggle.isSelected();
        boolean showExternalWorkExperience = externalWorkExperienceToggle.isSelected();
        boolean showInternalWorkExperience = internalWorkExperienceToggle.isSelected();

        employmentDetailsGrid.setVisible(showEmploymentDetails);
        employmentDetailsGrid.setManaged(showEmploymentDetails);
        leaveEntitlementGrid.setVisible(showLeaveEntitlement);
        leaveEntitlementGrid.setManaged(showLeaveEntitlement);
        externalWorkExperienceGrid.setVisible(showExternalWorkExperience);
        externalWorkExperienceGrid.setManaged(showExternalWorkExperience);
        internalWorkExperienceGrid.setVisible(showInternalWorkExperience);
        internalWorkExperienceGrid.setManaged(showInternalWorkExperience);
        editButton.setVisible(showEmploymentDetails);
        
        if (showLeaveEntitlement) {
            loadLeaveEntitlementData();
        } else if (showExternalWorkExperience) {
            loadExternalWorkExperienceData();
        } else if (showInternalWorkExperience) {
            loadInternalWorkExperienceData();
        }
    }

    private void loadLeaveEntitlementData() {
        try {
            Employee employee = EmployeeUtils.getEmployeeById(employeeId);
            if (employee != null) {
                int yearsOfService = EmployeeUtils.getTotalServiceYears(employeeId);
                
                int annualLeaveEntitlement = EmployeeUtils.getAnnualLeaveEntitlement(yearsOfService);
                int medicalLeaveEntitlement = EmployeeUtils.getMedicalLeaveEntitlement(yearsOfService);
                int maternityLeaveEntitlement = EmployeeUtils.getMaternityLeaveEntitlement(yearsOfService, employee.getGender());
                int unpaidLeaveEntitlement = EmployeeUtils.getUnpaidLeaveEntitlement();
                
                annualLeaveEntitled.setText(String.valueOf(annualLeaveEntitlement));
                medicalLeaveEntitled.setText(String.valueOf(medicalLeaveEntitlement));
                maternityLeaveEntitled.setText(String.valueOf(maternityLeaveEntitlement));
                unpaidLeaveEntitled.setText(String.valueOf(unpaidLeaveEntitlement));
                
                int annualLeaveTakenCount = EmployeeUtils.getTotalApprovedLeave(employeeId, "Annual Leave");
                int medicalLeaveTakenCount = EmployeeUtils.getTotalApprovedLeave(employeeId, "Medical Leave");
                int maternityLeaveTakenCount = EmployeeUtils.getTotalApprovedLeave(employeeId, "Maternity Leave");
                int unpaidLeaveTakenCount = EmployeeUtils.getTotalApprovedLeave(employeeId, "Unpaid Leave");
                
                annualLeaveTaken.setText(String.valueOf(annualLeaveTakenCount));
                medicalLeaveTaken.setText(String.valueOf(medicalLeaveTakenCount));
                maternityLeaveTaken.setText(String.valueOf(maternityLeaveTakenCount));
                unpaidLeaveTaken.setText(String.valueOf(unpaidLeaveTakenCount));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorAlert("Error loading leave entitlement data");
        }
    }

    private void loadExternalWorkExperienceData() {
        try {
            Employee employee = EmployeeUtils.getEmployeeById(employeeId);
            if (employee != null) {
                externalWorkExperienceGrid.getChildren().clear();
                
                // Add header
                addExternalWorkExperienceHeader();
                
                int row = 1;
                if (employee.getExternalWorkExperiences() != null) {
                    List<ExternalWorkExperience> experiences = employee.getExternalWorkExperiences();
                    for (int i = 0; i < experiences.size(); i++) {
                        addExternalWorkExperienceRow(experiences.get(i), row++, i);
                    }
                }
                
                // Add "Add" button (always add this, regardless of existing experiences)
                Button addButton = new Button("Add Experience");
                addButton.setOnAction(e -> handleAddExternalWorkExperience());
                externalWorkExperienceGrid.add(addButton, 0, row, 6, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorAlert("Error loading external work experience data");
        }
    }

    private void addExternalWorkExperienceHeader() {
        String[] headers = {"Company Name", "Position", "Start Date", "End Date", "Reason for Leaving", "Actions"};
        for (int i = 0; i < headers.length; i++) {
            Label headerLabel = new Label(headers[i]);
            headerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            externalWorkExperienceGrid.add(headerLabel, i, 0);
        }
    }

    private void addExternalWorkExperienceRow(ExternalWorkExperience exp, int row, int index) {
        Label companyName = new Label(exp.getCompanyName());
        Label position = new Label(exp.getPosition());
        Label startDate = new Label(exp.getStartDate());
        Label endDate = new Label(exp.getEndDate());
        Label reasonForLeaving = new Label(exp.getReasonForLeaving());
        
        companyName.setStyle("-fx-text-fill: white;");
        position.setStyle("-fx-text-fill: white;");
        startDate.setStyle("-fx-text-fill: white;");
        endDate.setStyle("-fx-text-fill: white;");
        reasonForLeaving.setStyle("-fx-text-fill: white;");
        
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> handleEditExternalWorkExperience(exp, index));
        
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> handleDeleteExternalWorkExperience(exp, index));
        
        HBox actions = new HBox(5, editButton, deleteButton);
        
        externalWorkExperienceGrid.addRow(row,
            companyName, position, startDate, endDate, reasonForLeaving, actions
        );
    }

    private void handleAddExternalWorkExperience() {
        ExternalWorkExperience newExp = showExternalWorkExperienceDialog(null);
        if (newExp != null) {
            try {
                Employee employee = EmployeeUtils.getEmployeeById(employeeId);
                if (employee != null) {
                    if (employee.getExternalWorkExperiences() == null) {
                        employee.setExternalWorkExperiences(new ArrayList<>());
                    }
                    employee.getExternalWorkExperiences().add(newExp);
                    Map<String, Employee> employees = EmployeeUtils.ReadData();
                    employees.put(employeeId, employee);
                    EmployeeUtils.WriteData(employees);
                    loadExternalWorkExperienceData();
                    SuccessAlert("External work experience added successfully");
                } else {
                    ErrorAlert("Employee not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorAlert("Error adding external work experience");
            }
        }
    }

    private void handleEditExternalWorkExperience(ExternalWorkExperience exp, int index) {
        ExternalWorkExperience updatedExp = showExternalWorkExperienceDialog(exp);
        if (updatedExp != null) {
            try {
                Employee employee = EmployeeUtils.getEmployeeById(employeeId);
                List<ExternalWorkExperience> experiences = employee.getExternalWorkExperiences();
                if (index >= 0 && index < experiences.size()) {
                    experiences.set(index, updatedExp);
                    Map<String, Employee> employees = EmployeeUtils.ReadData();
                    employees.put(employeeId, employee);
                    EmployeeUtils.WriteData(employees);
                    loadExternalWorkExperienceData();
                    SuccessAlert("External work experience updated successfully");
                } else {
                    ErrorAlert("Invalid index for external work experience");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorAlert("Error updating external work experience");
            }
        }
    }

    private void handleDeleteExternalWorkExperience(ExternalWorkExperience exp, int index) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete External Work Experience");
        confirmAlert.setContentText("Are you sure you want to delete this external work experience?");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Employee employee = EmployeeUtils.getEmployeeById(employeeId);
                    List<ExternalWorkExperience> experiences = employee.getExternalWorkExperiences();
                    if (index >= 0 && index < experiences.size()) {
                        experiences.remove(index);
                        Map<String, Employee> employees = EmployeeUtils.ReadData();
                        employees.put(employeeId, employee);
                        EmployeeUtils.WriteData(employees);
                        loadExternalWorkExperienceData();
                        SuccessAlert("External work experience deleted successfully");
                    } else {
                        ErrorAlert("Invalid index for external work experience");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorAlert("Error deleting external work experience");
                }
            }
        });
    }

    private ExternalWorkExperience showExternalWorkExperienceDialog(ExternalWorkExperience exp) {
        Dialog<ExternalWorkExperience> dialog = new Dialog<>();
        dialog.setTitle(exp == null ? "Add External Work Experience" : "Edit External Work Experience");
        dialog.setHeaderText(null);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField companyName = new TextField();
        TextField position = new TextField();
        TextField startDate = new TextField();
        TextField endDate = new TextField();
        TextField reasonForLeaving = new TextField();

        if (exp != null) {
            companyName.setText(exp.getCompanyName());
            position.setText(exp.getPosition());
            startDate.setText(exp.getStartDate());
            endDate.setText(exp.getEndDate());
            reasonForLeaving.setText(exp.getReasonForLeaving());
        }

        grid.add(new Label("Company Name:"), 0, 0);
        grid.add(companyName, 1, 0);
        grid.add(new Label("Position:"), 0, 1);
        grid.add(position, 1, 1);
        grid.add(new Label("Start Date (YYYY-MM-DD):"), 0, 2);
        grid.add(startDate, 1, 2);
        grid.add(new Label("End Date (YYYY-MM-DD):"), 0, 3);
        grid.add(endDate, 1, 3);
        grid.add(new Label("Reason for Leaving:"), 0, 4);
        grid.add(reasonForLeaving, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable save button depending on whether all fields are filled
        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        // Add listeners to all fields
        companyName.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(isAnyFieldEmpty(companyName, position, startDate, endDate, reasonForLeaving));
        });
        position.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(isAnyFieldEmpty(companyName, position, startDate, endDate, reasonForLeaving));
        });
        startDate.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(isAnyFieldEmpty(companyName, position, startDate, endDate, reasonForLeaving));
        });
        endDate.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(isAnyFieldEmpty(companyName, position, startDate, endDate, reasonForLeaving));
        });
        reasonForLeaving.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(isAnyFieldEmpty(companyName, position, startDate, endDate, reasonForLeaving));
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                if (isValidDate(startDate.getText()) && isValidDate(endDate.getText())) {
                    return new ExternalWorkExperience(
                        companyName.getText(),
                        position.getText(),
                        startDate.getText(),
                        endDate.getText(),
                        reasonForLeaving.getText()
                    );
                } else {
                    ErrorAlert("Invalid date format. Please use YYYY-MM-DD.");
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    private boolean isAnyFieldEmpty(TextField... fields) {
        for (TextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void loadInternalWorkExperienceData() {
        try {
            Employee employee = EmployeeUtils.getEmployeeById(employeeId);
            if (employee != null) {
                internalWorkExperienceGrid.getChildren().clear();
                
                // Add header
                addInternalWorkExperienceHeader();
                
                int row = 1;
                if (employee.getInternalWorkExperiences() != null) {
                    employee.sortInternalWorkExperiences();
                    List<InternalWorkExperience> experiences = employee.getInternalWorkExperiences();
                    for (int i = 0; i < experiences.size(); i++) {
                        addInternalWorkExperienceRow(experiences.get(i), row++, i);
                    }
                }
                
                // Add "Add" button (always add this, regardless of existing experiences)
                Button addButton = new Button("Add Experience");
                addButton.setOnAction(e -> handleAddInternalWorkExperience());
                internalWorkExperienceGrid.add(addButton, 0, row, 7, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorAlert("Error loading internal work experience data");
        }
    }

    private void addInternalWorkExperienceHeader() {
        String[] headers = {"Position", "Start Date", "End Date", "Department", "Gross Salary", "Salary Increment", "Actions"};
        for (int i = 0; i < headers.length; i++) {
            Label headerLabel = new Label(headers[i]);
            headerLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            internalWorkExperienceGrid.add(headerLabel, i, 0);
        }
    }

    private void addInternalWorkExperienceRow(InternalWorkExperience exp, int row, int index) {
        Label position = new Label(exp.getPosition());
        Label startDate = new Label(exp.getStartDate());
        Label endDate = new Label(exp.getEndDate());
        Label department = new Label(exp.getDepartment());
        Label grossSalary = new Label(String.valueOf(exp.getGrossSalary()));
        Label salaryIncrement = new Label("Calculating...");
        
        position.setStyle("-fx-text-fill: white;");
        startDate.setStyle("-fx-text-fill: white;");
        endDate.setStyle("-fx-text-fill: white;");
        department.setStyle("-fx-text-fill: white;");
        grossSalary.setStyle("-fx-text-fill: white;");
        salaryIncrement.setStyle("-fx-text-fill: white;");
        
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> handleEditInternalWorkExperience(exp, index));
        
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> handleDeleteInternalWorkExperience(exp, index));
        
        HBox actions = new HBox(5, editButton, deleteButton);
        
        internalWorkExperienceGrid.addRow(row,
            position, startDate, endDate, department, grossSalary, salaryIncrement, actions
        );

        // Calculate salary increment asynchronously
        new Thread(() -> {
            try {
                int increment = EmployeeUtils.calculateSalaryIncrement(employeeId, index);
                String incrementString = String.valueOf(increment);
                javafx.application.Platform.runLater(() -> {
                    salaryIncrement.setText(incrementString);
                });
            } catch (Exception e) {
                e.printStackTrace();
                javafx.application.Platform.runLater(() -> {
                    salaryIncrement.setText("Error");
                });
            }
        }).start();
    }

    private void handleAddInternalWorkExperience() {
        InternalWorkExperience newExp = showInternalWorkExperienceDialog(null);
        if (newExp != null) {
            try {
                Employee employee = EmployeeUtils.getEmployeeById(employeeId);
                if (employee != null) {
                    if (employee.getInternalWorkExperiences() == null) {
                        employee.setInternalWorkExperiences(new ArrayList<>());
                    }
                    
                    // Check if there's already an ongoing experience
                    boolean hasOngoing = employee.getInternalWorkExperiences().stream()
                        .anyMatch(exp -> "ongoing".equalsIgnoreCase(exp.getEndDate()));
                    
                    if (hasOngoing && "ongoing".equalsIgnoreCase(newExp.getEndDate())) {
                        ErrorAlert("There can't be two ongoing internal work experiences.");
                        return;
                    }
                    
                    employee.getInternalWorkExperiences().add(newExp);
                    
                    // If this is an ongoing experience, update the employee's main details
                    if ("ongoing".equalsIgnoreCase(newExp.getEndDate())) {
                        employee.setRole(newExp.getRole());
                        employee.setPosition(newExp.getPosition());
                        employee.setGrossSalary(newExp.getGrossSalary());
                        employee.setDepartment(newExp.getDepartment());
                    }
                    
                    Map<String, Employee> employees = EmployeeUtils.ReadData();
                    employees.put(employeeId, employee);
                    EmployeeUtils.WriteData(employees);
                    loadInternalWorkExperienceData();
                    loadEmployeeData(); // Refresh the main employee details
                    SuccessAlert("Internal work experience added successfully");
                } else {
                    ErrorAlert("Employee not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorAlert("Error adding internal work experience");
            }
        }
    }

    private void handleEditInternalWorkExperience(InternalWorkExperience exp, int index) {
        InternalWorkExperience updatedExp = showInternalWorkExperienceDialog(exp);
        if (updatedExp != null) {
            try {
                Employee employee = EmployeeUtils.getEmployeeById(employeeId);
                List<InternalWorkExperience> experiences = employee.getInternalWorkExperiences();
                if (index >= 0 && index < experiences.size()) {
                    // Check if there's already an ongoing experience (excluding the current one)
                    boolean hasOngoing = experiences.stream()
                        .filter(e -> e != exp)
                        .anyMatch(e -> "ongoing".equalsIgnoreCase(e.getEndDate()));
                    
                    if (hasOngoing && "ongoing".equalsIgnoreCase(updatedExp.getEndDate()) && !"ongoing".equalsIgnoreCase(exp.getEndDate())) {
                        ErrorAlert("There can't be two ongoing internal work experiences.");
                        return;
                    }
                    
                    experiences.set(index, updatedExp);
                    
                    // If this is an ongoing experience, update the employee's main details
                    if ("ongoing".equalsIgnoreCase(updatedExp.getEndDate())) {
                        employee.setRole(updatedExp.getRole());
                        employee.setPosition(updatedExp.getPosition());
                        employee.setGrossSalary(updatedExp.getGrossSalary());
                        employee.setDepartment(updatedExp.getDepartment());
                    }
                    
                    Map<String, Employee> employees = EmployeeUtils.ReadData();
                    employees.put(employeeId, employee);
                    EmployeeUtils.WriteData(employees);
                    loadInternalWorkExperienceData();
                    loadEmployeeData(); // Refresh the main employee details
                    SuccessAlert("Internal work experience updated successfully");
                } else {
                    ErrorAlert("Invalid index for internal work experience");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorAlert("Error updating internal work experience");
            }
        }
    }

    private void handleDeleteInternalWorkExperience(InternalWorkExperience exp, int index) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Internal Work Experience");
        confirmAlert.setContentText("Are you sure you want to delete this internal work experience?");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Employee employee = EmployeeUtils.getEmployeeById(employeeId);
                    List<InternalWorkExperience> experiences = employee.getInternalWorkExperiences();
                    if (index >= 0 && index < experiences.size()) {
                        experiences.remove(index);
                        Map<String, Employee> employees = EmployeeUtils.ReadData();
                        employees.put(employeeId, employee);
                        EmployeeUtils.WriteData(employees);
                        loadInternalWorkExperienceData();
                        SuccessAlert("Internal work experience deleted successfully");
                    } else {
                        ErrorAlert("Invalid index for internal work experience");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ErrorAlert("Error deleting internal work experience");
                }
            }
        });
    }

    private InternalWorkExperience showInternalWorkExperienceDialog(InternalWorkExperience exp) {
        Dialog<InternalWorkExperience> dialog = new Dialog<>();
        dialog.setTitle(exp == null ? "Add Internal Work Experience" : "Edit Internal Work Experience");
        dialog.setHeaderText(null);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField position = new TextField();
        TextField startDate = new TextField();
        TextField endDate = new TextField();
        TextField department = new TextField();
        TextField grossSalary = new TextField();
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("System Administrator", "Normal Employee", "HR Officer", "Department Manager", "Payroll Officer");

        if (exp != null) {
            position.setText(exp.getPosition());
            startDate.setText(exp.getStartDate());
            endDate.setText(exp.getEndDate());
            department.setText(exp.getDepartment());
            grossSalary.setText(String.valueOf(exp.getGrossSalary()));
            roleComboBox.setValue(exp.getRole());
        }

        grid.add(new Label("Position:"), 0, 0);
        grid.add(position, 1, 0);
        grid.add(new Label("Start Date (YYYY-MM-DD):"), 0, 1);
        grid.add(startDate, 1, 1);
        grid.add(new Label("End Date (YYYY-MM-DD or 'ongoing'):"), 0, 2);
        grid.add(endDate, 1, 2);
        grid.add(new Label("Department:"), 0, 3);
        grid.add(department, 1, 3);
        grid.add(new Label("Gross Salary:"), 0, 4);
        grid.add(grossSalary, 1, 4);
        grid.add(new Label("Role:"), 0, 5);
        grid.add(roleComboBox, 1, 5);

        dialog.getDialogPane().setContent(grid);

        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        position.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(isAnyFieldEmpty(position, startDate, endDate, department, grossSalary) || roleComboBox.getValue() == null);
        });
        startDate.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(isAnyFieldEmpty(position, startDate, endDate, department, grossSalary) || roleComboBox.getValue() == null);
        });
        endDate.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(isAnyFieldEmpty(position, startDate, endDate, department, grossSalary) || roleComboBox.getValue() == null);
        });
        department.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(isAnyFieldEmpty(position, startDate, endDate, department, grossSalary) || roleComboBox.getValue() == null);
        });
        grossSalary.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(isAnyFieldEmpty(position, startDate, endDate, department, grossSalary) || roleComboBox.getValue() == null);
        });
        roleComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(isAnyFieldEmpty(position, startDate, endDate, department, grossSalary) || newValue == null);
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                if (isValidDate(startDate.getText()) && (isValidDate(endDate.getText()) || "ongoing".equalsIgnoreCase(endDate.getText()))) {
                    try {
                        return new InternalWorkExperience(
                            position.getText(),
                            startDate.getText(),
                            endDate.getText(),
                            department.getText(),
                            Integer.parseInt(grossSalary.getText()),
                            roleComboBox.getValue()
                        );
                    } catch (NumberFormatException e) {
                        ErrorAlert("Invalid gross salary. Please enter a valid integer.");
                        return null;
                    }
                } else {
                    ErrorAlert("Invalid date format. Please use YYYY-MM-DD or 'ongoing' for end date.");
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }
}
