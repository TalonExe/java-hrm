package com.talon.utils;

import com.talon.models.Employee;
import com.talon.models.InternalWorkExperience;
import com.talon.models.LeaveApplication;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.Period;
import java.time.LocalTime;
import java.util.ArrayList;
import com.talon.models.AttendanceRecord;

public class EmployeeUtils {
    private static final String FILE_PATH = "data/employeeList.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Map<String, Employee> ReadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            return gson.fromJson(reader, new TypeToken<Map<String, Employee>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void WriteData(Map<String, Employee> employeeList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            gson.toJson(employeeList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Employee findByUsername(String username) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            Employee output = null;
            for (Employee employee : employees.values()) {
                if (employee.getUsername().equalsIgnoreCase(username)) {
                    output = employee;
                    break;
                }
            }
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Employee getEmployeeById(String id) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            return employees.get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateLoginStatus(String username, String loginStatus) throws Exception {
        try {
            Employee employee = findByUsername(username);
            Map<String, Employee> employees = ReadData();
            if (loginStatus.equalsIgnoreCase("SUCCESS")) {
                employee.setLoginAttempts(0);
                employee.setAccountStatus("ACTIVE");
            } else {
                employee.setLoginAttempts(employee.getLoginAttempts() + 1);
                if (employee.getLoginAttempts() >= 3) {
                    employee.setAccountStatus("LOCKED");
                }
            }
            // Find employee id
            String employeeId = null;
            for (String key : employees.keySet()) {
                if (employees.get(key).getUsername().equalsIgnoreCase(username)) {
                    employeeId = key;
                    break;
                }
            }
            employees.put(employeeId, employee);
            WriteData(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified;
    }

    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static Employee createEmployee(String username, String password, String role) throws Exception {
        try {
            // Check if username already exists
            Map<String, Employee> employees = ReadData();
            for (Employee employee : employees.values()) {
                if (employee.getUsername().equalsIgnoreCase(username)) {
                    throw new Exception("Username already exists");
                }
            }
            String hashedPassword = hashPassword(password);
            String id = UUID.randomUUID().toString();
            Employee newEmployee = new Employee(username, hashedPassword, role);
            newEmployee.setLoginAttempts(0);
            newEmployee.setAccountStatus("ACTIVE");
            newEmployee.setAccountDisabled(false);
            employees.put(id, newEmployee);
            WriteData(employees);
            return employees.get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean changePassword(String employeeId, String newPassword) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            Employee employee = employees.get(employeeId);
            if (employee != null) {
                String hashedPassword = hashPassword(newPassword);
                employee.setPassword(hashedPassword);
                WriteData(employees);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void updateRole(String username, String newRole) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            employees.get(username).setRole(newRole);
            WriteData(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unlockAccount(String key) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            employees.get(key).setAccountStatus("ACTIVE");
            employees.get(key).setLoginAttempts(0);
            WriteData(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disableAccount(String key, String command) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            if (command.equalsIgnoreCase("disable")) {
                employees.get(key).setAccountDisabled(true);
            } else {
                employees.get(key).setAccountDisabled(false);
            }
            WriteData(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getTotalApprovedLeave(String id, String leaveType) {
        try {
            Employee employee = getEmployeeById(id);
            if (employee == null || employee.getLeaveApplications() == null) {
                return 0;
            }

            int currentYear = LocalDate.now().getYear();
            int totalApprovedLeave = 0;

            for (LeaveApplication leaveApplication : employee.getLeaveApplications()) {
                LocalDate startDate = LocalDate.parse(leaveApplication.getStartDate());
                if (leaveApplication.getLeaveType().equalsIgnoreCase(leaveType) 
                    && leaveApplication.getStatus().equalsIgnoreCase("APPROVED") 
                    && startDate.getYear() == currentYear) {
                    totalApprovedLeave++;
                }
            }
            return totalApprovedLeave;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getTotalServiceYears(String id) throws Exception {
        try {
            Employee employee = getEmployeeById(id);
            List<InternalWorkExperience> internalWorkExperiences = employee.getInternalWorkExperiences();
            
            if (internalWorkExperiences == null || internalWorkExperiences.isEmpty()) {
                return 0;
            }

            LocalDate earliestStartDate = null;
            LocalDate latestEndDate = null;

            for (InternalWorkExperience experience : internalWorkExperiences) {
                LocalDate startDate = LocalDate.parse(experience.getStartDate());
                LocalDate endDate = experience.getEndDate().equalsIgnoreCase("ongoing") 
                    ? LocalDate.now() 
                    : LocalDate.parse(experience.getEndDate());

                if (earliestStartDate == null || startDate.isBefore(earliestStartDate)) {
                    earliestStartDate = startDate;
                }

                if (latestEndDate == null || endDate.isAfter(latestEndDate)) {
                    latestEndDate = endDate;
                }
            }

            Period period = Period.between(earliestStartDate, latestEndDate);
            return period.getYears();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getAnnualLeaveEntitlement(int yearsOfService) {
        if (yearsOfService < 2) {
            return 8;
        } else if (yearsOfService < 5) {
            return 12;
        } else {
            return 16;
        }
    }

    public static int getMedicalLeaveEntitlement(int yearsOfService) {
        if (yearsOfService < 2) {
            return 14;
        } else if (yearsOfService < 5) {
            return 18;
        } else {
            return 22;
        }
    }

    public static int getMaternityLeaveEntitlement(int yearsOfService, String gender) {
        if (gender != null && gender.equalsIgnoreCase("female")) {
            return 98;
        }
        return 0;
    }

    public static int getUnpaidLeaveEntitlement() {
        return 14;
    }

    public static void createAttendanceRecord(String id, String date, String timeIn) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            Employee employee = employees.get(id);
            
            if (employee == null) {
                throw new Exception("Employee not found");
            }
            
            List<AttendanceRecord> attendanceRecords = employee.getAttendanceRecords();
            if (attendanceRecords == null) {
                attendanceRecords = new ArrayList<>();
            }
            
            // Check if an attendance record for today already exists
            AttendanceRecord todayRecord = attendanceRecords.stream()
                .filter(record -> record.getDate().equals(date))
                .findFirst()
                .orElse(null);
            
            if (todayRecord == null) {
                // Create a new attendance record
                String status = isLate(timeIn) ? "LATE" : "ONTIME";
                AttendanceRecord newRecord = new AttendanceRecord(date, status, timeIn, "PENDING");
                attendanceRecords.add(newRecord);
                employee.setAttendanceRecords(attendanceRecords);
                employees.put(id, employee);
                WriteData(employees);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void clockOut(String id, String date, String timeOut) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            Employee employee = employees.get(id);
            
            if (employee == null) {
                throw new Exception("Employee not found");
            }
            
            List<AttendanceRecord> attendanceRecords = employee.getAttendanceRecords();
            if (attendanceRecords == null) {
                throw new Exception("No attendance records found");
            }
            
            // Find the attendance record for today
            AttendanceRecord todayRecord = attendanceRecords.stream()
                .filter(record -> record.getDate().equals(date))
                .findFirst()
                .orElse(null);
            
            if (todayRecord == null) {
                throw new Exception("No clock-in record found for today");
            }
            
            // Update the clock-out time
            todayRecord.setTimeOut(timeOut);
            
            // Update the employee's attendance records
            employee.setAttendanceRecords(attendanceRecords);
            employees.put(id, employee);
            WriteData(employees);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static int calculateSalaryIncrement(String id, int indexOfExperience) throws Exception {
        try {
            Map<String, Employee> employees = ReadData();
            Employee employee = employees.get(id);
            
            if (employee == null) {
                throw new Exception("Employee not found");
            }
            
            List<InternalWorkExperience> internalWorkExperiences = employee.getInternalWorkExperiences();
            if (internalWorkExperiences == null || internalWorkExperiences.isEmpty()) {
                return 0;
            }

            // Sort internalWorkExperiences by start date
            internalWorkExperiences.sort((e1, e2) -> e1.getStartDate().compareTo(e2.getStartDate()));

            // If it's the first experience or invalid index, return 0
            if (indexOfExperience <= 0 || indexOfExperience >= internalWorkExperiences.size()) {
                return 0;
            }

            InternalWorkExperience experience = internalWorkExperiences.get(indexOfExperience);
            int grossSalary = experience.getGrossSalary();

            InternalWorkExperience previousExperience = internalWorkExperiences.get(indexOfExperience - 1);
            int previousGrossSalary = previousExperience.getGrossSalary();

            // calculate increment
            return grossSalary - previousGrossSalary;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static boolean isLate(String timeIn) {
        LocalTime clockInTime = LocalTime.parse(timeIn);
        LocalTime lateThreshold = LocalTime.of(8, 30);
        return clockInTime.isAfter(lateThreshold);
    }
}