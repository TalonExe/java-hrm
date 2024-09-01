package com.talon.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SystemAdministratorController extends EmployeeController {

    @FXML
    private void handleAddUser(ActionEvent event) {
        // Logic to add a user
        showAlert("Add User", "This would open the Add User dialog.");
    }

    @FXML
    private void handleUpdateUser(ActionEvent event) {
        // Logic to update a user
        showAlert("Update User", "This would open the Update User dialog.");
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        // Logic to delete a user
        showAlert("Delete User", "This would open the Delete User dialog.");
    }

    @FXML
    private void handleViewUsers(ActionEvent event) {
        // Logic to view users
        showAlert("View Users", "This would display the list of users.");
    }

    @FXML
    private void handleUnlockAccounts(ActionEvent event) {
        // Logic to unlock accounts
        showAlert("Unlock Accounts", "This would unlock user accounts.");
    }

    @FXML
    private void handleSettings(ActionEvent event) {
        // Logic to open settings
        showAlert("System Settings", "This would open the System Settings dialog.");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        // Logic to log out
        showAlert("Logout", "This would log the user out.");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}