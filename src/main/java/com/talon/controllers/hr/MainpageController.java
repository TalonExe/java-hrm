package com.talon.controllers.hr;

import com.talon.controllers.BaseController;
import com.talon.models.Employee;
import com.talon.utils.EmployeeUtils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.Map;
import java.io.IOException;

public class MainpageController extends BaseController {
    // Table
    @FXML
    private TableView<HomeTableRow> mainTable;

    // Column
    @FXML
    private TableColumn<HomeTableRow, Button> viewMoreColumn;

    @FXML
    public void initialize() {
        loadTableData();
    }

    public void loadTableData() {
        // Load data from database
        if (mainTable != null) {
            changeValueOfViewMoreColumn();
            mainTable.getItems().clear();
            Map<String, Employee> employees = EmployeeUtils.ReadData();

            int index = 1;
            for (Map.Entry<String, Employee> entry : employees.entrySet()) {
                String id = entry.getKey();
                Employee employee = entry.getValue();
                Button viewMoreButton = new Button("View More");
                viewMoreButton.setStyle("-fx-cursor: hand;");
                viewMoreButton.setOnAction(event -> {
                    handleViewMore(id);
                });
                String username = employee.getUsername();
                String fullName = employee.getFullName() != null ? employee.getFullName() : "N/A";
                String role = employee.getRole();
                HomeTableRow row = new HomeTableRow(index++, username, fullName, role, viewMoreButton);
                mainTable.getItems().add(row);
            }
        }
    }

    private void changeValueOfViewMoreColumn() {
        if (viewMoreColumn != null) {
            viewMoreColumn.setCellValueFactory(new PropertyValueFactory<>("viewMore"));
            viewMoreColumn.setCellFactory(column -> new TableCell<HomeTableRow, Button>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(item);
                        item.setOnAction(event -> {
                            HomeTableRow row = getTableView().getItems().get(getIndex());
                            String username = row.getUsername(); // Assuming HomeTableRow has getId() method
                            Map<String, Employee> employees = EmployeeUtils.ReadData();
                            // Loop through the employees map to find the employee with the matching username
                            for (Map.Entry<String, Employee> entry : employees.entrySet()) {
                                if (entry.getValue().getUsername().equals(username)) {
                                    String id = entry.getKey();
                                    handleViewMore(id);
                                    break;
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private void refreshLobbyTable() {
        mainTable.getItems().clear();
        loadTableData();
    }

    @FXML
    private void handleViewMore(String employeeId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/hr/EmployeePersonalInfoModal.fxml"));
            Parent root = loader.load();
            EmployeePersonalInfoModalController currentController = loader.getController();
            currentController.initData(employeeId);
            
            // Set the refresh callback
            currentController.setRefreshCallback(new EmployeePersonalInfoModalController.RefreshCallback() {
                @Override
                public void refreshTable() {
                    refreshLobbyTable();
                }
            });

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            WarningAlert("Error opening employee details: " + e.getMessage());
        }
    }
}
