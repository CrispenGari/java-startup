package com.thymeleaf.thymeleaf.user;
import lombok.*;
import java.sql.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String email;
    private String password;
    private String gender;
    private String note;
    private boolean married;
    private Date birthday;
    private String profession;
}
