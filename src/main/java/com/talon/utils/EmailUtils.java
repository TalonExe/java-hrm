package com.talon.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtils {
    private static final String SMTP_HOST = "smtp-mail.outlook.com";
    private static final String SMTP_PORT = "587";
    private static final String USERNAME = "talonleavereply@hotmail.com";
    private static final String PASSWORD = "ql9g9oB04fK8qIv";

    public static void sendLeaveApplicationNotification(String recipientEmail, String employeeName, String leaveType, String startDate, String endDate, boolean isApproved) {
        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipient email is required");
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            
            String subject = isApproved ? "Leave Application Approved" : "Leave Application Rejected";
            message.setSubject(subject);

            String status = isApproved ? "approved" : "rejected";
            String content = "Dear " + (employeeName != null ? employeeName : "Employee") + ",\n\n" +
                    "Your leave application has been " + status + ".\n\n" +
                    "Leave Details:\n" +
                    "Type: " + (leaveType != null ? leaveType : "N/A") + "\n" +
                    "Start Date: " + (startDate != null ? startDate : "N/A") + "\n" +
                    "End Date: " + (endDate != null ? endDate : "N/A") + "\n\n" +
                    "If you have any questions, please contact the HR department.\n\n" +
                    "Best regards,\n" +
                    "HR Team";

            message.setText(content);

            Transport.send(message);

            System.out.println("Leave application notification email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send leave application notification email to " + recipientEmail);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
