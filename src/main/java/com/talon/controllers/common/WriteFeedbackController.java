package com.talon.controllers.common;

import com.talon.controllers.BaseController;
import com.talon.models.Employee;
import com.talon.models.Feedback;
import com.talon.utils.EmployeeUtils;
import com.talon.utils.FeedbackUtils;
import com.talon.utils.SessionState;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class WriteFeedbackController extends BaseController {

    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private void handleSubmitFeedback() {
        String feedbackText = feedbackTextArea.getText().trim();
        
        if (feedbackText.isEmpty()) {
            ErrorAlert("Please enter your feedback before submitting.");
            return;
        }

        try {
            String employeeId = SessionState.getInstance().getLoggedInUserId();
            Employee employee = EmployeeUtils.getEmployeeById(employeeId);

            if (employee == null) {
                ErrorAlert("Unable to retrieve employee information.");
                return;
            }

            Feedback feedback = new Feedback(
                feedbackText,
                employee.getRole(),
                LocalDate.now().toString()
            );

            Map<String, Feedback> feedbackList = FeedbackUtils.ReadData();
            String feedbackId = UUID.randomUUID().toString();
            feedbackList.put(feedbackId, feedback);
            FeedbackUtils.WriteData(feedbackList);

            SuccessAlert("Thank you for your feedback!");
            feedbackTextArea.clear();
        } catch (Exception e) {
            ErrorAlert("An error occurred while submitting your feedback: " + e.getMessage());
        }
    }
}
