package com.mysql.mysql.employee.exceptions;

public class EmployeeNotFoundException extends RuntimeException{
    public  EmployeeNotFoundException(String message){
        super(message);
    }
}
