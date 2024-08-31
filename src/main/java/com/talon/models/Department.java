package com.talon.models;

import java.io.IOException;

public class Department {
    EmployeeManager empManager;
    DepartmentManager manager;

public Department(DepartmentManager manager) {
    setManager(manager);
    try {
        this.empManager = new EmployeeManager();
    } catch (IOException e) {
    }
}

public DepartmentManager getManager() {
    return manager;
}

public void setManager(DepartmentManager manager) {
    this.manager = manager;
}


}
