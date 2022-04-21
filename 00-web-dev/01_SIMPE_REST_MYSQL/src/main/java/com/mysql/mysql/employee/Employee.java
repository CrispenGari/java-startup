package com.mysql.mysql.employee;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "employee_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    public Employee(){}
    public Employee(String name, String email){
        this.name = name;
        this.email= email;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
