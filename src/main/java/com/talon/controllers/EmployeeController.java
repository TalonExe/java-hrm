package com.talon.controllers;

import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.talon.models.DepartmentManager;
import com.talon.models.EmployeeAdapter;

public class EmployeeController {
    GsonBuilder builder = new GsonBuilder().registerTypeAdapter(DepartmentManager.class, new EmployeeAdapter());
    Gson gson = builder.create();
    JsonReader jread;

     
    public EmployeeController() throws IOException{
        try {
            jread = new JsonReader(new InputStreamReader(getClass().getResourceAsStream("/data/employeeList.json")));
            System.out.println(jread+"hi");
        } catch (Exception e) {
            System.err.println(e+"hi");
        }  
        
        DepartmentManager manager = gson.fromJson(jread, DepartmentManager.class);
        System.out.println(manager);

    } 


}
