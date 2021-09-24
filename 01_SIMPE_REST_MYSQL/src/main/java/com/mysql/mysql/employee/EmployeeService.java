package com.mysql.mysql.employee;

import com.mysql.mysql.employee.exceptions.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository repository;
    @Autowired
    public EmployeeService(EmployeeRepository repository){
        this.repository = repository;
    }

    // Adding employees to the database
     public Employee addEmployee(Employee employee){
        return  this.repository.save(employee);
     }
    // Getting all employees
    public List<Employee> getEmployees(){
        return this.repository.findAll();
    }
    // Getting a single employee
    public  Employee getEmployee(Long id){
        return this.repository.findById(id).orElseThrow(
                ()-> new EmployeeNotFoundException("the employee with that id was not found."));
    }
    // Updating an employee
    public Employee updateEmployee(String email, Long id){
        Employee employee = this.repository.findById(id).orElseThrow(()-> new EmployeeNotFoundException("the employee with that id was not found."));
        if(email != null && !email.equals(employee.getEmail())){
            employee.setEmail(email);
        }
        return employee;
    }
    // deleting an employee

    public Boolean deleteEmployee(Long id){
        this.repository.deleteById(id);
        return true;
    }
    // delete all employees
    public Boolean deleteAllEmployees(){
        this.repository.deleteAll();
        return true;
    }

}
