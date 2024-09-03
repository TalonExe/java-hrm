package com.talon.controllers;

import com.talon.models.Employee;
import com.talon.utils.EmployeeUtils;
import com.talon.components.HRO.LobbyRow;
import com.talon.components.HRO.EmployeePersonal;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Map;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;

public class HROController extends EmployeeController{

    // Table
    @FXML
    private TableView<LobbyRow> lobbyTable;

    // Columns
    @FXML
    private TableColumn<LobbyRow, Button> viewMoreColumn;

    // Switch scenes
    @FXML
    private void switchToEmployeeEmergencyContact(){
    }

    @FXML
    private void switchToEmployeeWorkExperience(){
    }   

    @FXML
    private void initialize(){
        loadLobbyTableData();
    }

    public void refreshLobbyTable(){
        loadLobbyTableData();
        changeValueOfViewMoreColumn();
    }

    private void loadLobbyTableData(){
        // Load employees into the tableview
        if (lobbyTable != null) {
            changeValueOfViewMoreColumn();
            lobbyTable.getItems().clear();
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
                LobbyRow row = new LobbyRow(index++, username, fullName, role, viewMoreButton);
                lobbyTable.getItems().add(row);
            }
        }
    }

    private void changeValueOfViewMoreColumn(){
        // Change the value of the view more column
        if (viewMoreColumn != null) {
            viewMoreColumn.setCellValueFactory(new PropertyValueFactory<>("viewMore"));
            viewMoreColumn.setCellFactory(new Callback<TableColumn<LobbyRow, Button>, TableCell<LobbyRow, Button>>() {
                @Override
                public TableCell<LobbyRow, Button> call(TableColumn<LobbyRow, Button> param) {
                    return new TableCell<LobbyRow, Button>() {
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

    @FXML
    private void handleViewMore(String employeeId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/hr/EmployeeProfile.fxml"));
            Parent root = loader.load();
            EmployeePersonal controller = loader.getController();
            controller.initData(employeeId);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
