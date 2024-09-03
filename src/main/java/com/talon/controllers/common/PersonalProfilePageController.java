package com.talon.controllers.common;

import com.talon.controllers.BaseController;
import com.talon.models.*;
import com.talon.utils.EmployeeUtils;
import com.talon.utils.SessionState;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class PersonalProfilePageController extends BaseController {

    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField genderField;
    @FXML private TextField idPassportField;
    @FXML private TextField nationalityField;
    @FXML private TextField birthDateField;
    @FXML private TextField phoneNumberField;
    @FXML private TextArea addressField;

    @FXML private TextField roleField;
    @FXML private TextField positionField;
    @FXML private TextField departmentField;
    @FXML private TextField grossSalaryField;

    @FXML private TextField emergencyNameField;
    @FXML private TextField emergencyPhoneField;
    @FXML private TextField emergencyRelationshipField;

    @FXML private TextField annualLeaveEntitled;
    @FXML private TextField annualLeaveTaken;
    @FXML private TextField medicalLeaveEntitled;
    @FXML private TextField medicalLeaveTaken;
    @FXML private TextField maternityLeaveEntitled;
    @FXML private TextField maternityLeaveTaken;
    @FXML private TextField unpaidLeaveEntitled;
    @FXML private TextField unpaidLeaveTaken;

    @FXML private TableView<InternalWorkExperience> internalWorkExperienceTable;
    @FXML private TableColumn<InternalWorkExperience, String> internalPositionColumn;
    @FXML private TableColumn<InternalWorkExperience, String> internalDepartmentColumn;
    @FXML private TableColumn<InternalWorkExperience, String> internalStartDateColumn;
    @FXML private TableColumn<InternalWorkExperience, String> internalEndDateColumn;
    @FXML private TableColumn<InternalWorkExperience, Integer> internalGrossSalaryColumn;

    @FXML private TableView<ExternalWorkExperience> externalWorkExperienceTable;
    @FXML private TableColumn<ExternalWorkExperience, String> externalCompanyNameColumn;
    @FXML private TableColumn<ExternalWorkExperience, String> externalPositionColumn;
    @FXML private TableColumn<ExternalWorkExperience, String> externalStartDateColumn;
    @FXML private TableColumn<ExternalWorkExperience, String> externalEndDateColumn;
    @FXML private TableColumn<ExternalWorkExperience, String> externalReasonForLeavingColumn;

    @FXML private ComboBox<String> attendanceMonthComboBox;
    @FXML private ComboBox<String> attendanceYearComboBox;
    @FXML private TableView<AttendanceRecord> attendanceTable;
    @FXML private TableColumn<AttendanceRecord, String> attendanceDateColumn;
    @FXML private TableColumn<AttendanceRecord, String> attendanceStatusColumn;
    @FXML private TableColumn<AttendanceRecord, String> attendanceTimeInColumn;
    @FXML private TableColumn<AttendanceRecord, String> attendanceTimeOutColumn;

    private Employee currentEmployee;

    @FXML
    public void initialize() {
        setupTables();
        setupAttendanceComboBoxes();
        
        // Add null checks
        if (annualLeaveEntitled == null || annualLeaveTaken == null ||
            medicalLeaveEntitled == null || medicalLeaveTaken == null ||
            maternityLeaveEntitled == null || maternityLeaveTaken == null ||
            unpaidLeaveEntitled == null || unpaidLeaveTaken == null) {
            System.err.println("One or more leave TextFields are null. Check your FXML file.");
        }
    }

    public void loadUserData() {
        loadEmployeeData();
    }

    private void loadEmployeeData() {
        String employeeId = SessionState.getInstance().getLoggedInUserId();
        if (employeeId == null || employeeId.isEmpty()) {
            System.err.println("Error: No logged-in user ID found in session");
            ErrorAlert("Failed to load employee data: No user ID found");
            router.switchScene("login");
            return;
        }
        
        try {
            currentEmployee = EmployeeUtils.getEmployeeById(employeeId);
            if (currentEmployee != null) {
                updatePersonalInfo();
                updateEmploymentDetails();
                updateEmergencyContact();
                updateInternalWorkExperience();
                updateExternalWorkExperience();
                updateLeaveEntitlement();
            } else {
                System.err.println("Error: Employee not found for ID: " + employeeId);
                ErrorAlert("Failed to load employee data: Employee not found");
                router.switchScene("login");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading employee data: " + e.getMessage());
            ErrorAlert("Error loading employee data: " + e.getMessage());
            router.switchScene("login");
        }
    }

    private void updatePersonalInfo() {
        fullNameField.setText(currentEmployee.getFullName());
        emailField.setText(currentEmployee.getEmail());
        genderField.setText(currentEmployee.getGender());
        idPassportField.setText(currentEmployee.getIdentificationCard() != null ? 
                                currentEmployee.getIdentificationCard() : currentEmployee.getPassport());
        nationalityField.setText(currentEmployee.getNationality());
        birthDateField.setText(currentEmployee.getBirthDate());
        phoneNumberField.setText(currentEmployee.getPhoneNumber());
        addressField.setText(currentEmployee.getAddress());
    }

    private void updateEmploymentDetails() {
        roleField.setText(currentEmployee.getRole());
        positionField.setText(currentEmployee.getPosition());
        departmentField.setText(currentEmployee.getDepartment());
        grossSalaryField.setText(String.valueOf(currentEmployee.getGrossSalary()));
    }

    private void updateEmergencyContact() {
        EmergencyContact emergencyContact = currentEmployee.getEmergencyContact();
        if (emergencyContact != null) {
            emergencyNameField.setText(emergencyContact.getName());
            emergencyPhoneField.setText(emergencyContact.getPhoneNumber());
            emergencyRelationshipField.setText(emergencyContact.getRelationship());
        }
    }

    private void updateLeaveEntitlement() {
        String employeeId = SessionState.getInstance().getLoggedInUserId();
        if (employeeId == null || employeeId.isEmpty()) {
            System.err.println("Error: No logged-in user ID found in session");
            ErrorAlert("Failed to load employee data: No user ID found");
            router.switchScene("login");
            return;
        }
        
        try {
            int yearsOfService = EmployeeUtils.getTotalServiceYears(employeeId);

            int annualLeaveEntitledDays = EmployeeUtils.getAnnualLeaveEntitlement(yearsOfService);
            int medicalLeaveEntitledDays = EmployeeUtils.getMedicalLeaveEntitlement(yearsOfService);
            int maternityLeaveEntitledDays = EmployeeUtils.getMaternityLeaveEntitlement(yearsOfService, currentEmployee.getGender());
            int unpaidLeaveEntitledDays = EmployeeUtils.getUnpaidLeaveEntitlement();
        
            if (annualLeaveEntitled != null) annualLeaveEntitled.setText(String.valueOf(annualLeaveEntitledDays));
            if (medicalLeaveEntitled != null) medicalLeaveEntitled.setText(String.valueOf(medicalLeaveEntitledDays));
            if (maternityLeaveEntitled != null) maternityLeaveEntitled.setText(String.valueOf(maternityLeaveEntitledDays));
            if (unpaidLeaveEntitled != null) unpaidLeaveEntitled.setText(String.valueOf(unpaidLeaveEntitledDays));
        
            if (annualLeaveTaken != null) annualLeaveTaken.setText(String.valueOf(calculateTakenLeaveDays("Annual Leave")));
            if (medicalLeaveTaken != null) medicalLeaveTaken.setText(String.valueOf(calculateTakenLeaveDays("Medical Leave")));
            if (maternityLeaveTaken != null) maternityLeaveTaken.setText(String.valueOf(calculateTakenLeaveDays("Maternity Leave")));
            if (unpaidLeaveTaken != null) unpaidLeaveTaken.setText(String.valueOf(calculateTakenLeaveDays("Unpaid Leave")));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error updating leave entitlement: " + e.getMessage());
            ErrorAlert("Error updating leave entitlement: " + e.getMessage());
        }
    }

    private int calculateTakenLeaveDays(String leaveType) {
        if (currentEmployee == null || currentEmployee.getLeaveApplications() == null) {
            return 0;
        }

        int currentYear = LocalDate.now().getYear();
        return (int) currentEmployee.getLeaveApplications().stream()
            .filter(leave -> leave.getLeaveType().equalsIgnoreCase(leaveType)
                && leave.getStatus().equalsIgnoreCase("APPROVED")
                && LocalDate.parse(leave.getStartDate()).getYear() == currentYear)
            .count();
    }

    private void setupTables() {
        // Internal Work Experience Table
        internalPositionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        internalDepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        internalStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        internalEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        internalGrossSalaryColumn.setCellValueFactory(new PropertyValueFactory<>("grossSalary"));

        // External Work Experience Table
        externalCompanyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        externalPositionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        externalStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        externalEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        externalReasonForLeavingColumn.setCellValueFactory(new PropertyValueFactory<>("reasonForLeaving"));

        // Attendance Table
        attendanceDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        attendanceStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        attendanceTimeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        attendanceTimeOutColumn.setCellValueFactory(new PropertyValueFactory<>("timeOut"));
    }

    private void updateInternalWorkExperience() {
        List<InternalWorkExperience> internalExperiences = currentEmployee.getInternalWorkExperiences();
        if (internalExperiences != null) {
            internalWorkExperienceTable.setItems(FXCollections.observableArrayList(internalExperiences));
        }
    }

    private void updateExternalWorkExperience() {
        List<ExternalWorkExperience> externalExperiences = currentEmployee.getExternalWorkExperiences();
        if (externalExperiences != null) {
            externalWorkExperienceTable.setItems(FXCollections.observableArrayList(externalExperiences));
        }
    }

    private void setupAttendanceComboBoxes() {
        attendanceMonthComboBox.setItems(FXCollections.observableArrayList(
            "January", "February", "March", "April", "May", "June", 
            "July", "August", "September", "October", "November", "December"
        ));

        int currentYear = LocalDate.now().getYear();
        attendanceYearComboBox.setItems(FXCollections.observableArrayList(
            String.valueOf(currentYear - 1),
            String.valueOf(currentYear),
            String.valueOf(currentYear + 1)
        ));
    }

    @FXML
    private void handleViewAttendance() {
        String selectedMonth = attendanceMonthComboBox.getValue();
        String selectedYear = attendanceYearComboBox.getValue();

        if (selectedMonth == null || selectedYear == null) {
            ErrorAlert("Please select both month and year");
            return;
        }

        List<AttendanceRecord> filteredRecords = filterAttendanceRecords(selectedMonth, selectedYear);
        attendanceTable.setItems(FXCollections.observableArrayList(filteredRecords));
    }

    private List<AttendanceRecord> filterAttendanceRecords(String month, String year) {
        List<AttendanceRecord> allRecords = currentEmployee.getAttendanceRecords();
        if (allRecords == null) {
            return new ArrayList<>();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int selectedYear = Integer.parseInt(year);
        int selectedMonth = LocalDate.parse(year + "-" + month + "-01", DateTimeFormatter.ofPattern("yyyy-MMMM-dd")).getMonthValue();

        return allRecords.stream()
            .filter(record -> {
                LocalDate recordDate = LocalDate.parse(record.getDate(), formatter);
                return recordDate.getYear() == selectedYear && recordDate.getMonthValue() == selectedMonth;
            })
            .collect(Collectors.toList());
    }
}