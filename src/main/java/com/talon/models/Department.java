package com.talon.models;

public class Department {
 private Employee[] users = new Employee[10];
 private DepartmentManager manager;
 
public DepartmentManager getManager() {
    return manager;
}

public Employee[] getUsers() {
    return users;
}

public void setManager(DepartmentManager manager) {
    this.manager = manager;
}

public void setUsers(Employee[] users) {
    this.users = users;
}

}
