package com.talon.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.util.Callback;
import com.talon.models.Employee;
import com.talon.utils.EmployeeUtils;
import com.talon.components.SystemAdmin.UserManagementRow;
import com.talon.components.SystemAdmin.HomepageTableRow;
import com.talon.components.SystemAdmin.EditEmployeeModal;
import com.talon.Router;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class SystemAdministratorController extends EmployeeController {
    private final Router route = Router.getInstance();

    // Tables
    @FXML
    private TableView<HomepageTableRow> homepageTable;

    @FXML
    private TableView<UserManagementRow> userManagementTable;

    // Columns
    @FXML
    private TableColumn<HomepageTableRow, Button> accountStatusColumn; // Declare it directly

    @FXML
    private TableColumn<UserManagementRow, Button> editButtonColumn;

    @FXML
    private TableColumn<UserManagementRow, Button> disableButtonColumn;

    @FXML
    public void initialize() {
        loadHomepageData();
        loadManageUserData();
    }

    @FXML
    private void switchToHome() {
        route.switchToScene("SystemAdminHomepage");
        refreshHomepage();
    }

    @FXML
    private void switchToManageUser() {
        route.switchToScene("ManageUser");
        refreshManageUserData();
    }

    @FXML
    private void showEditEmployeeModal(Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SystemAdmin/EditEmployeeModal.fxml"));
            AnchorPane modalRoot = loader.load();
            EditEmployeeModal controller = loader.getController();
            controller.setEmployeeData(employee);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(userManagementTable.getScene().getWindow());
            stage.setScene(new Scene(modalRoot));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void refreshHomepage() {
        ChangeValueOfAccountStatusColumn();
        loadHomepageData();
    }

    private void refreshManageUserData() {
        changeValueOfUserManagementActionColumns();
        loadManageUserData();
    }

    private void loadHomepageData() {
        if (homepageTable != null) {
            ChangeValueOfAccountStatusColumn();
            homepageTable.getItems().clear();
            Map<String, Employee> employees = EmployeeUtils.ReadData();
            int index = 1;

            for (Map.Entry<String, Employee> entry : employees.entrySet()) {
                String userKey = entry.getKey();
                Employee employee = entry.getValue();
                Button accountStatusButton = new Button(
                        employee.getAccountStatus().equalsIgnoreCase("LOCKED") ? "UNLOCK" : "ACTIVE");
                if (accountStatusButton.getText().equals("UNLOCK")) {
                    accountStatusButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-cursor: hand;");
                } else {
                    accountStatusButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    accountStatusButton.setDisable(true);
                }
                accountStatusButton.setOnAction(event -> {
                    if (employee.getAccountStatus().equalsIgnoreCase("LOCKED")) {
                        unlockAccount(userKey);
                    }
                });
                HomepageTableRow row = new HomepageTableRow(index++, employee.getUsername(), employee.getRole(),
                        accountStatusButton);
                homepageTable.getItems().add(row);
            }
        }
    }

    private void loadManageUserData() {
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
                    showEditEmployeeModal(employee);
                });
                disableButton.setOnAction(event -> {
                    if (employee.getAccountDisabled()) {
                        disableAccount(userKey, "enable");
                    } else {
                        disableAccount(userKey, "disable");
                    }
                });
                UserManagementRow row = new UserManagementRow(index++,
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
                    new Callback<TableColumn<UserManagementRow, Button>, TableCell<UserManagementRow, Button>>() {
                        @Override
                        public TableCell<UserManagementRow, Button> call(TableColumn<UserManagementRow, Button> param) {
                            return new TableCell<UserManagementRow, Button>() {
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
                    new Callback<TableColumn<UserManagementRow, Button>, TableCell<UserManagementRow, Button>>() {
                        @Override
                        public TableCell<UserManagementRow, Button> call(TableColumn<UserManagementRow, Button> param) {
                            return new TableCell<UserManagementRow, Button>() {
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

    private void ChangeValueOfAccountStatusColumn() {
        if (accountStatusColumn == null) {
            System.out.println("accountStatusColumn is null");
            return;
        }
        // Initialize the column correctly without casting
        accountStatusColumn.setCellValueFactory(new PropertyValueFactory<>("accountStatusButton"));
        accountStatusColumn.setCellFactory(
                new Callback<TableColumn<HomepageTableRow, Button>, TableCell<HomepageTableRow, Button>>() {
                    @Override
                    public TableCell<HomepageTableRow, Button> call(TableColumn<HomepageTableRow, Button> param) {
                        return new TableCell<HomepageTableRow, Button>() {
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

    private void unlockAccount(String userKey) {
        try {
            EmployeeUtils.unlockAccount(userKey);
            refreshHomepage();
        } catch (Exception e) {
            e.printStackTrace();
        }
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