
# Java HRM

## Disclaimer

For **non x86 machines**,  comment the javafx maven plugin in the pom.xml before running.

## Prerequisites
Please Install **Java** and ***Maven** on your machine.

## Installation

Clone project

```bash
git clone https://github.com/TalonExe/java-hrm.git
cd ./java-hrm
```
    
## Running

```bash
mvn javafx:run
```

## Running on non x86 machines
Install Dependencies
```bash
mvn clean install
```
```bash
mvn clean compile
```
```bash
mvn exec:java -Dexec.mainClass="com.talon.App"
```

## System Requirements
Database:
JSON files

All Users:
- Reset own password
- Locked Account after 3 failed attempts
- View Employee Profile
- Apply Leave
- Cancel Leave
- Check the status of leave application 
- Clock in when logged in
- Clock out when logged out
- Create Late attendance
- Deduct 100 from gross salary if late for 3 days and above in a month
- Read monthly and annual attendance

System Administrator:
- Create user
- Read User
- Update User
- Delete / Disable User
- Unlock User Account

HRO:
- Create Profile
- Read Profile
- Update Profile

Department Manager:
- View Leave Application
- Approve Leave Application
- Reject Leave Application

Payroll Officer:
- Create monthly payroll transaction
- Read payroll transaction
- Update monthly payroll transaction
- Generate monthly payslip
