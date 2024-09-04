package com.talon.controllers.systemAdmin;

import com.talon.models.Employee;
import com.talon.utils.EmployeeUtils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.util.Map;

public class ManageUserAccountsPageController extends SystemAdminMainController {

    // Table
    @FXML
    private TableView<ManageUserAccountsPageTableRow> userManagementTable;

    // Column
    @FXML
    private TableColumn<ManageUserAccountsPageTableRow, Button> editButtonColumn;

    @FXML
    private TableColumn<ManageUserAccountsPageTableRow, Button> disableButtonColumn;

    @FXML
    private void initialize() {
        loadTableData();
    }

    @FXML
    private void showCreateUserModal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/systemAdmin/CreateUserModal.fxml"));
            AnchorPane modalRoot = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(userManagementTable.getScene().getWindow());
            stage.setScene(new Scene(modalRoot));
            stage.showAndWait();
            refreshManageUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showEditEmployeeModal(String employeeId, Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/systemAdmin/EditEmployeeModal.fxml"));
            AnchorPane modalRoot = loader.load();
            EditEmployeeModal controller = loader.getController();
            controller.setEmployeeData(employeeId, employee);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(userManagementTable.getScene().getWindow());
            stage.setScene(new Scene(modalRoot));
            stage.showAndWait();
            refreshManageUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadTableData() {
        if (userManagementTable != null) {
            changeValueOfUserManagementActionColumns();
            userManagementTable.getItems().clear();
            Map<String, Employee> employees = EmployeeUtils.ReadData();

            int index = 1;
            for (Map.Entry<String, Employee> entry : employees.entrySet()) {
                String userKey = entry.getKey();
                Employee employee = entry.getValue();
                Button editButton = new Button("Edit");
                Button disableButton = new Button(employee.getAccountDisabled() ? "Enable" : "Disable");
                editButton.setStyle("-fx-cursor: hand;");
                if (disableButton.getText().equals("Enable")) {
                    disableButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-cursor: hand;");
                } else {
                    disableButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-cursor: hand;");
                }
                // Show edit user dialog
                editButton.setOnAction(event -> {
                    // Implement edit functionality here
                    showEditEmployeeModal(userKey, employee);
                });
                disableButton.setOnAction(event -> {
                    if (employee.getAccountDisabled()) {
                        disableAccount(userKey, "enable");
                    } else {
                        disableAccount(userKey, "disable");
                    }
                });
                ManageUserAccountsPageTableRow row = new ManageUserAccountsPageTableRow(index++,
                        employee.getUsername(), employee.getRole(), editButton, disableButton);
                userManagementTable.getItems().add(row);
            }
            userManagementTable.refresh(); // Refresh the table view
        }
    }

    private void changeValueOfUserManagementActionColumns() {
        if (editButtonColumn != null) {
            editButtonColumn.setCellValueFactory(new PropertyValueFactory<>("editButton"));
            editButtonColumn.setCellFactory(
                    new Callback<TableColumn<ManageUserAccountsPageTableRow, Button>, TableCell<ManageUserAccountsPageTableRow, Button>>() {
                        @Override
                        public TableCell<ManageUserAccountsPageTableRow, Button> call(
                                TableColumn<ManageUserAccountsPageTableRow, Button> param) {
                            return new TableCell<ManageUserAccountsPageTableRow, Button>() {
                                @Override
                                protected void updateItem(Button item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty || item == null) {
                                        setGraphic(null);
                                    } else {
                                        setGraphic(item);
                                    }
                                }
                            };
                        }
                    });

        }
        if (disableButtonColumn != null) {
            disableButtonColumn.setCellValueFactory(new PropertyValueFactory<>("disableButton"));
            disableButtonColumn.setCellFactory(
                    new Callback<TableColumn<ManageUserAccountsPageTableRow, Button>, TableCell<ManageUserAccountsPageTableRow, Button>>() {
                        @Override
                        public TableCell<ManageUserAccountsPageTableRow, Button> call(
                                TableColumn<ManageUserAccountsPageTableRow, Button> param) {
                            return new TableCell<ManageUserAccountsPageTableRow, Button>() {
                                @Override
                                protected void updateItem(Button item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty || item == null) {
                                        setGraphic(null);
                                    } else {
                                        setGraphic(item);
                                    }
                                }
                            };
                        }
                    });
        }
    }

    public void refreshManageUserData() {
        loadTableData();
        changeValueOfUserManagementActionColumns();
    }

    private void disableAccount(String userKey, String command) {
        try {
            EmployeeUtils.disableAccount(userKey, command);
            refreshManageUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
