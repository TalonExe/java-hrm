package com.talon.models;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class EmployeeAdapter extends TypeAdapter<DepartmentManager>{
    @Override
    public void write(JsonWriter out, DepartmentManager value) throws IOException {
      
        
    }

    @Override
    public DepartmentManager read(JsonReader in) throws IOException {
        DepartmentManager manager = new DepartmentManager();
        in.beginObject();
        String fieldname = null;

        while (in.hasNext()) { 
            JsonToken token = in.peek();            
            
            if (token.equals(JsonToken.NAME)) {     
                //get the current token 
                fieldname = in.nextName(); 
            } 
            
            if ("username".equals(fieldname)) {       
                //move to next token 
                token = in.peek(); 
                manager.setUsername(in.nextString()); 
            } 
            
            if("password".equals(fieldname)) { 
                //move to next token 
                token = in.peek(); 
                manager.setPassword(in.nextString()); 
            }

            if("name".equals(fieldname)) { 
                //move to next token 
                token = in.peek(); 
                manager.setName(in.nextString()); 
            } 

            if("passport".equals(fieldname)) { 
                //move to next token 
                token = in.peek(); 
                manager.setPassport(in.nextString()); 
            }

            if("identificationCard".equals(fieldname)) { 
                //move to next token 
                token = in.peek(); 
                manager.setIdentificationCard(in.nextString()); 
            }  

            if("phoneNumber".equals(fieldname)) { 
                //move to next token 
                token = in.peek(); 
                manager.setPhoneNumber(in.nextString()); 
            }   

            if("birthDate".equals(fieldname)) { 
                //move to next token 
                token = in.peek(); 
                manager.setBirthDate(in.nextString()); 
            }  

            if("email".equals(fieldname)) { 
                //move to next token 
                token = in.peek(); 
                manager.setEmail(in.nextString()); 
            }  

            if("address".equals(fieldname)) { 
                //move to next token 
                token = in.peek(); 
                manager.setAddress(in.nextString()); 
            }  

            if("emergencyContact".equals(fieldname)) { 
                //move to next token 
                token = in.peek(); 
                manager.setEmergencyContact(in.nextString()); 
            }  

            if("role".equals(fieldname)) { 
                //move to next token 
                token = in.peek(); 
                manager.setRole(in.nextString()); 
            }

            if("position".equals(fieldname)) { 
                //move to next token 
                token = in.peek(); 
                manager.setPosition(in.nextString()); 
            }  
        } 
        in.endObject(); 
        return manager; 

    }
}
