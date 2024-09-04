package com.talon.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.talon.models.PayrollTransaction;
import com.talon.models.Employee;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class PayslipGenerator {
    
    public static void generatePayslip(PayrollTransaction transaction, String outputPath) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        // Add company logo (replace with your actual logo path)
        Image logo = Image.getInstance("src/main/resources/images/logo.png");
        logo.scaleToFit(100, 100);
        logo.setAlignment(Element.ALIGN_CENTER);
        document.add(logo);

        // Add company name and payslip title
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph title = new Paragraph("Talon", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph payslipTitle = new Paragraph("Monthly Payslip", titleFont);
        payslipTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(payslipTitle);

        document.add(Chunk.NEWLINE);

        // Add employee details
        try {
            Employee employee = EmployeeUtils.getEmployeeById(transaction.getEmployeeId());
            addEmployeeDetails(document, employee, transaction);
            
            // Add salary details
            addSalaryDetails(document, transaction);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately, maybe add an error message to the document
            document.add(new Paragraph("Error: Unable to retrieve employee details"));
        }

        document.close();
    }

    private static void addEmployeeDetails(Document document, Employee employee, PayrollTransaction transaction) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        // find key for employee by their username
        Map<String, Employee> employees = EmployeeUtils.ReadData();
        String key = employees.entrySet().stream()
            .filter(entry -> entry.getValue().getUsername().equals(employee.getUsername()))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);

        addTableCell(table, "Employee ID:", key);
        addTableCell(table, "Employee Name:", employee.getFullName());
        addTableCell(table, "Department:", employee.getDepartment());
        addTableCell(table, "Position:", employee.getPosition());
        addTableCell(table, "Pay Period:", getPayPeriod(transaction.getTransactionDate()));

        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private static void addSalaryDetails(Document document, PayrollTransaction transaction) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        addTableCell(table, "Gross Salary:", String.format("RM %.2f", transaction.getGrossSalary()));
        addTableCell(table, "Employee EPF:", String.format("RM %.2f", transaction.getEmployeeEpf()));
        addTableCell(table, "Employee SOCSO:", String.format("RM %.2f", transaction.getEmployeeSocso()));
        addTableCell(table, "Employee EIS:", String.format("RM %.2f", transaction.getEmployeeEis()));
        addTableCell(table, "PCB (Tax):", String.format("RM %.2f", transaction.getPcb()));
        addTableCell(table, "Late Fee:", String.format("RM %.2f", transaction.getLatePenalty()));
        addTableCell(table, "Total Deductions:", String.format("RM %.2f", 
            transaction.getEmployeeEpf() + transaction.getEmployeeSocso() + 
            transaction.getEmployeeEis() + transaction.getPcb() + transaction.getLatePenalty()));

        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        PdfPCell netSalaryLabel = new PdfPCell(new Phrase("Net Salary:", boldFont));
        PdfPCell netSalaryValue = new PdfPCell(new Phrase(String.format("RM %.2f", transaction.getNetSalary()), boldFont));
        netSalaryLabel.setBackgroundColor(BaseColor.LIGHT_GRAY);
        netSalaryValue.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(netSalaryLabel);
        table.addCell(netSalaryValue);

        document.add(table);
    }

    private static void addTableCell(PdfPTable table, String label, String value) {
        table.addCell(new Phrase(label, new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
        table.addCell(new Phrase(value, new Font(Font.FontFamily.HELVETICA, 10)));
    }

    private static String getPayPeriod(String transactionDate) {
        LocalDate date = LocalDate.parse(transactionDate);
        LocalDate startOfMonth = date.withDayOfMonth(1);
        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return startOfMonth.format(formatter) + " - " + endOfMonth.format(formatter);
    }
}
