package com.talon.components.HRO;

import com.talon.models.Employee;
import com.talon.utils.EmployeeUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import java.util.Map;
import com.talon.models.EmergencyContact;
import java.time.LocalDate;
import java.time.Period;

public class EmployeePersonal {

    @FXML
    private Button editButton;
    @FXML
    private Button toggleViewButton;
    @FXML
    private VBox personalInfoBox;
    @FXML
    private VBox emergencyContactBox;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private TextField idPassportField;
    @FXML
    private TextField nationalityField;
    @FXML
    private TextField birthDateField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emergencyNameField;
    @FXML
    private TextField emergencyPhoneField;
    @FXML
    private TextField emergencyRelationshipField;
    @FXML
    private TextArea addressField;

    private String employeeId;
    private Employee employee;
    private boolean isPersonalInfoView = true;

    public void initData(String employeeId) {
        this.employeeId = employeeId;
        try {
            employee = EmployeeUtils.getEmployeeById(employeeId);
        } catch (Exception e) {
            e.printStackTrace();
            showWarningAlert("Failed to retrieve employee data");
        }
        if (employee != null) {
            updateFields();
        }
    }

    private void updateFields() {
        fullNameField.setText(employee.getFullName());
        emailField.setText(employee.getEmail());
        genderComboBox.setValue(employee.getGender());
        addressField.setText(employee.getAddress());
        idPassportField.setText(
                employee.getIdentificationCard() != null ? employee.getIdentificationCard() : employee.getPassport());
        nationalityField.setText(employee.getNationality());
        birthDateField.setText(employee.getBirthDate());
        phoneNumberField.setText(employee.getPhoneNumber());

        if (employee.getEmergencyContact() != null) {
            emergencyNameField.setText(employee.getEmergencyContact().getName());
            emergencyPhoneField.setText(employee.getEmergencyContact().getPhoneNumber());
            emergencyRelationshipField.setText(employee.getEmergencyContact().getRelationship());
        }
    }

    @FXML
    private void initialize() {
        genderComboBox.getItems().addAll("Male", "Female");
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
            employee.setFullName(fullNameField.getText().trim());
            employee.setEmail(emailField.getText().trim());
            employee.setGender(genderComboBox.getValue());
            employee.setAddress(addressField.getText().trim());
            employee.setIdentificationCard(idPassportField.getText().trim());
            employee.setPassport(null);
            employee.setNationality(nationalityField.getText().trim());
            employee.setBirthDate(birthDateField.getText().trim());
            employee.setPhoneNumber(phoneNumberField.getText().trim());

            if (employee.getEmergencyContact() != null) {
                employee.getEmergencyContact().setName(emergencyNameField.getText().trim());
                employee.getEmergencyContact().setPhoneNumber(emergencyPhoneField.getText().trim());
                employee.getEmergencyContact().setRelationship(emergencyRelationshipField.getText().trim());
            } else {
                EmergencyContact emergencyContact = new EmergencyContact(emergencyNameField.getText().trim(),
                        emergencyPhoneField.getText().trim(), emergencyRelationshipField.getText().trim());
                employee.setEmergencyContact(emergencyContact);
            }

            employees.put(employeeId, employee);
            EmployeeUtils.WriteData(employees);
            showSuccessAlert("Changes saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            showWarningAlert("Invalid Input");
        }
    }

    private boolean validateInputs() {
        if (fullNameField.getText().trim().isEmpty()) {
            showWarningAlert("Full name cannot be empty");
            return false;
        }
        if (!isValidEmail(emailField.getText().trim())) {
            showWarningAlert("Invalid email format");
            return false;
        }
        if (genderComboBox.getValue() == null) {
            showWarningAlert("Gender must be selected");
            return false;
        }
        if (!isValidAge(birthDateField.getText().trim())) {
            showWarningAlert("Employee must be between 18 and 80 years old");
            return false;
        }
        if (!isValidAddress(addressField.getText().trim())) {
            showWarningAlert("Address cannot be empty and must be between 5 and 200 characters");
            return false;
        }
        if (idPassportField.getText().trim().isEmpty()) {
            showWarningAlert("ID/Passport cannot be empty");
            return false;
        }
        if (nationalityField.getText().trim().isEmpty()) {
            showWarningAlert("Nationality cannot be empty");
            return false;
        }
        if (!isValidDate(birthDateField.getText().trim())) {
            showWarningAlert("Invalid birth date format (use YYYY-MM-DD)");
            return false;
        }
        if (!isValidPhoneNumber(phoneNumberField.getText().trim())) {
            showWarningAlert("Invalid phone number format");
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
        isPersonalInfoView = !isPersonalInfoView;
        personalInfoBox.setVisible(isPersonalInfoView);
        emergencyContactBox.setVisible(!isPersonalInfoView);
        toggleViewButton.setText(isPersonalInfoView ? "View Emergency Contact" : "View Personal Info");
    }

    private void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}