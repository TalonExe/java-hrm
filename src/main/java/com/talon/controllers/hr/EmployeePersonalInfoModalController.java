package com.talon.controllers.hr;

import com.talon.models.Employee;
import com.talon.utils.EmployeeUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.Map;
import com.talon.models.EmergencyContact;
import java.time.LocalDate;
import java.time.Period;

public class EmployeePersonalInfoModalController extends HRMainController {

    @FXML private Button editButton;
    @FXML private Button toggleViewButton;
    @FXML private VBox personalInfoBox;
    @FXML private VBox emergencyContactBox;
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField idPassportField;
    @FXML private TextField nationalityField;
    @FXML private TextField birthDateField;
    @FXML private TextField phoneNumberField;
    @FXML private TextArea addressField;
    @FXML private TextField emergencyNameField;
    @FXML private TextField emergencyPhoneField;
    @FXML private TextField emergencyRelationshipField;

    private String employeeId;
    private Employee employee;
    private boolean isPersonalInfoView = true;

    public interface RefreshCallback {
        void refreshTable();
    }

    private RefreshCallback refreshCallback;

    public void setRefreshCallback(RefreshCallback callback) {
        this.refreshCallback = callback;
    }

    public void initData(String employeeId) {
        this.employeeId = employeeId;
        System.out.println("Initializing data for employee ID: " + employeeId);
        try {
            this.employee = EmployeeUtils.getEmployeeById(employeeId);
            if (this.employee != null) {
                updateFields();
            } else {
                System.out.println("Employee not found for ID: " + employeeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorAlert("Failed to retrieve employee data");
        }
    }

    private void updateFields() {
        if (this.employee != null) {
            System.out.println("Updating fields for employee: " + this.employee.getFullName());
            fullNameField.setText(this.employee.getFullName());
            emailField.setText(this.employee.getEmail());
            genderComboBox.setValue(this.employee.getGender());
            if (addressField != null) {
                addressField.setText(this.employee.getAddress());
            }
            idPassportField.setText(
                    this.employee.getIdentificationCard() != null ? this.employee.getIdentificationCard() : this.employee.getPassport());
            nationalityField.setText(this.employee.getNationality());
            birthDateField.setText(this.employee.getBirthDate());
            phoneNumberField.setText(this.employee.getPhoneNumber());

            if (this.employee.getEmergencyContact() != null) {
                emergencyNameField.setText(this.employee.getEmergencyContact().getName());
                emergencyPhoneField.setText(this.employee.getEmergencyContact().getPhoneNumber());
                emergencyRelationshipField.setText(this.employee.getEmergencyContact().getRelationship());
            }
        }
    }

    @FXML
    protected void initialize() {
        genderComboBox.getItems().addAll("Male", "Female");
        System.out.println("personalInfoBox: " + (personalInfoBox != null ? "injected" : "null"));
        System.out.println("emergencyContactBox: " + (emergencyContactBox != null ? "injected" : "null"));
    }

    @FXML
    private void handleEdit() {
        boolean isEditable = !fullNameField.isEditable();
        fullNameField.setEditable(isEditable);
        emailField.setEditable(isEditable);
        genderComboBox.setDisable(!isEditable);
        addressField.setEditable(isEditable);
        idPassportField.setEditable(isEditable);
        nationalityField.setEditable(isEditable);
        birthDateField.setEditable(isEditable);
        phoneNumberField.setEditable(isEditable);
        emergencyNameField.setEditable(isEditable);
        emergencyPhoneField.setEditable(isEditable);
        emergencyRelationshipField.setEditable(isEditable);

        editButton.setText(isEditable ? "Save" : "Edit");

        if (!isEditable) {
            saveChanges();
        }
    }

    private void saveChanges() {
        try {
            // Validate input fields
            if (!validateInputs()) {
                return;
            }

            Map<String, Employee> employees = EmployeeUtils.ReadData();
            this.employee.setFullName(fullNameField.getText().trim());
            this.employee.setEmail(emailField.getText().trim());
            this.employee.setGender(genderComboBox.getValue());
            this.employee.setAddress(addressField.getText().trim());
            this.employee.setIdentificationCard(idPassportField.getText().trim());
            this.employee.setPassport(null);
            this.employee.setNationality(nationalityField.getText().trim());
            this.employee.setBirthDate(birthDateField.getText().trim());
            this.employee.setPhoneNumber(phoneNumberField.getText().trim());

            if (this.employee.getEmergencyContact() != null) {
                this.employee.getEmergencyContact().setName(emergencyNameField.getText().trim());
                this.employee.getEmergencyContact().setPhoneNumber(emergencyPhoneField.getText().trim());
                this.employee.getEmergencyContact().setRelationship(emergencyRelationshipField.getText().trim());
            } else {
                EmergencyContact emergencyContact = new EmergencyContact(emergencyNameField.getText().trim(),
                        emergencyPhoneField.getText().trim(), emergencyRelationshipField.getText().trim());
                this.employee.setEmergencyContact(emergencyContact);
            }

            employees.put(employeeId, this.employee);
            EmployeeUtils.WriteData(employees);
            showSuccessAlert("Changes saved successfully");

            // Call the refresh callback
            if (refreshCallback != null) {
                refreshCallback.refreshTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorAlert("Invalid Input");
        }
    }

    private boolean validateInputs() {
        if (fullNameField.getText().trim().isEmpty()) {
            ErrorAlert("Full name cannot be empty");
            return false;
        }
        if (!isValidEmail(emailField.getText().trim())) {
            ErrorAlert("Invalid email format");
            return false;
        }
        if (genderComboBox.getValue() == null) {
            ErrorAlert("Gender must be selected");
            return false;
        }
        if (!isValidAge(birthDateField.getText().trim())) {
            ErrorAlert("Employee must be between 18 and 80 years old");
            return false;
        }
        if (!isValidAddress(addressField.getText().trim())) {
            ErrorAlert("Address cannot be empty and must be between 5 and 200 characters");
            return false;
        }
        if (idPassportField.getText().trim().isEmpty()) {
            ErrorAlert("ID/Passport cannot be empty");
            return false;
        }
        if (nationalityField.getText().trim().isEmpty()) {
            ErrorAlert("Nationality cannot be empty");
            return false;
        }
        if (!isValidDate(birthDateField.getText().trim())) {
            ErrorAlert("Invalid birth date format (use YYYY-MM-DD)");
            return false;
        }
        if (!isValidPhoneNumber(phoneNumberField.getText().trim())) {
            ErrorAlert("Invalid phone number format");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    private boolean isValidDate(String date) {
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        return date.matches(dateRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\+?\\d{10,14}$";
        return phoneNumber.matches(phoneRegex);
    }

    private boolean isValidAge(String birthDateStr) {
        try {
            LocalDate birthDate = LocalDate.parse(birthDateStr);
            LocalDate now = LocalDate.now();
            int age = Period.between(birthDate, now).getYears();
            return age >= 18 && age <= 80;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidAddress(String address) {
        return address.length() >= 5 && address.length() <= 200;
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void toggleView() {
        System.out.println("Toggling view. Current view: " + (isPersonalInfoView ? "Personal Info" : "Emergency Contact"));
        isPersonalInfoView = !isPersonalInfoView;
        personalInfoBox.setVisible(isPersonalInfoView);
        personalInfoBox.setManaged(isPersonalInfoView);
        emergencyContactBox.setVisible(!isPersonalInfoView);
        emergencyContactBox.setManaged(!isPersonalInfoView);
        toggleViewButton.setText(isPersonalInfoView ? "View Emergency Contact" : "View Personal Info");
    }
}