package com.talon.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailUtils {
    private static final String FROM_EMAIL = "talonleavereply@hotmail.com";
    private static final String PASSWORD = "ql9g9oB04fK8qIv";

    public static boolean sendLeaveApplicationNotification(String recipientEmail, String employeeName, String leaveType, String startDate, String endDate, boolean isApproved) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Leave Application " + (isApproved ? "Approved" : "Rejected"));

            String content = "Dear " + employeeName + ",\n\n" +
                    "Your " + leaveType + " application for the period " + startDate + " to " + endDate + " has been " +
                    (isApproved ? "approved" : "rejected") + ".\n\n" +
                    "If you have any questions, please contact the HR department.\n\n" +
                    "Best regards,\nHR Team";

            message.setText(content);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
