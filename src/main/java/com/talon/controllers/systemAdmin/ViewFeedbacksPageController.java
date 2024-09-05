package com.talon.controllers.systemAdmin;

import com.talon.models.Feedback;
import com.talon.utils.FeedbackUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Map;

public class ViewFeedbacksPageController extends SystemAdminMainController {

    @FXML
    private TableView<FeedbackTableRow> feedbackTable;

    @FXML
    private TableColumn<FeedbackTableRow, Integer> numberColumn;

    @FXML
    private TableColumn<FeedbackTableRow, String> userTypeColumn;

    @FXML
    private TableColumn<FeedbackTableRow, String> dateColumn;

    @FXML
    private TableColumn<FeedbackTableRow, String> feedbackColumn;

    @FXML
    private void initialize() {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        userTypeColumn.setCellValueFactory(new PropertyValueFactory<>("userType"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        feedbackColumn.setCellValueFactory(new PropertyValueFactory<>("feedback"));

        loadFeedbacks();
    }

    public void loadFeedbacks() {
        feedbackTable.getItems().clear();
        Map<String, Feedback> feedbacks = FeedbackUtils.ReadData();
        int index = 1;
        for (Map.Entry<String, Feedback> entry : feedbacks.entrySet()) {
            Feedback feedback = entry.getValue();
            feedbackTable.getItems().add(new FeedbackTableRow(
                index++,
                feedback.getUserType(),
                feedback.getDate(),
                feedback.getFeedback()
            ));
        }
    }

    public void refreshFeedbacks() {
        loadFeedbacks();
    }
}
