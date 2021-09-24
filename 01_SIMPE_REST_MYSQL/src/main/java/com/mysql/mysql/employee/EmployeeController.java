package com.mysql.mysql.employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/employees")
public class EmployeeController {
    private  final  EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Employee>> getAll(){
        return new ResponseEntity<List<Employee>>(this.service.getEmployees(), HttpStatus.OK);
    }
    @GetMapping(path = "/one/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long id){
        return  new ResponseEntity<Employee>(this.service.getEmployee(id), HttpStatus.OK);
    }
    @PostMapping(path = "/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        return  new ResponseEntity<Employee>(
                this.service.addEmployee(employee), HttpStatus.CREATED
        );
    }
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee,
                                                   @PathVariable("id") Long id){
        return  new ResponseEntity<Employee>(
                this.service.updateEmployee(employee.getEmail(), id), HttpStatus.OK
        );
    }
    @DeleteMapping(path = "/delete-one/{id}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable("id") Long id){
        return  new ResponseEntity<Boolean>(this.service.deleteEmployee(id), HttpStatus.OK);
    }
    @DeleteMapping(path = "/delete-all")
    public ResponseEntity<Boolean> deleteAll(){
        return  new ResponseEntity<Boolean>(this.service.deleteAllEmployees(), HttpStatus.OK);
    }
}
