package com.talon.controllers.systemAdmin;

import com.talon.models.Employee;
import com.talon.utils.EmployeeUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import java.util.Map;

public class HomepageController extends SystemAdminMainController {
    //Table Data
    @FXML
    private TableView<HomepageTableRow> homepageTable;

    @FXML
    private TableColumn<HomepageTableRow, Button> accountStatusColumn;    

    @FXML
    private void initialize() {
        loadTableData();
    }

    @Override
    protected void loadTableData() {
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

    public void refreshHomepage() {
        ChangeValueOfAccountStatusColumn();
        loadTableData();
    }

    private void unlockAccount(String userKey) {
        try {
            EmployeeUtils.unlockAccount(userKey);
            refreshHomepage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
