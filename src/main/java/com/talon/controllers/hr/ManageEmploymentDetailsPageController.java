package com.talon.controllers.hr;

import com.talon.models.Employee;
import com.talon.utils.EmployeeUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ManageEmploymentDetailsPageController extends HRMainController {
    // Table
    @FXML
    private TableView<ManageEmploymentDetailsRow> employmentDetailsTable;

    // Columns
    @FXML
    private TableColumn<ManageEmploymentDetailsRow, Button> viewMoreColumn;

    @FXML
    protected void initialize() {
        super.initialize();
        if (employmentDetailsTable != null) {
            employmentDetailsTable.prefWidthProperty()
                    .bind(((VBox) employmentDetailsTable.getParent()).widthProperty().subtract(60));
            setupTable();
        }
    }

    public void setupTable() {
        if (employmentDetailsTable != null) {
            changeValueOfViewMoreColumn();
            employmentDetailsTable.getItems().clear();
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
                ManageEmploymentDetailsRow row = new ManageEmploymentDetailsRow(index++, employee.getUsername(),
                        employee.getFullName(),
                        employee.getRole(), employee.getPosition(), employee.getDepartment(), viewMoreButton);
                employmentDetailsTable.getItems().add(row);
            }
        }
    }

    private void changeValueOfViewMoreColumn() {
        if (viewMoreColumn != null) {
            viewMoreColumn.setCellValueFactory(new PropertyValueFactory<>("viewMore"));
            viewMoreColumn.setCellFactory(column -> new TableCell<ManageEmploymentDetailsRow, Button>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(item);
                        item.setOnAction(event -> {
                            ManageEmploymentDetailsRow row = getTableView().getItems().get(getIndex());
                            String username = row.getUsername();
                            Map<String, Employee> employees = EmployeeUtils.ReadData();
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

    @FXML
    private void handleViewMore(String employeeId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/hr/ViewEmploymentDetailsPage.fxml"));
            Parent root = loader.load();
            ViewEmploymentDetailsPageController controller = loader.getController();
            controller.initData(employeeId);

            Scene scene = new Scene(root);
            router.getPrimaryStage().setScene(scene);
            router.getPrimaryStage().setTitle("View Employment Details");
        } catch (IOException e) {
            e.printStackTrace();
            ErrorAlert("Error loading View Employment Details page");
        }
    }
}
