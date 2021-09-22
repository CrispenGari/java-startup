package com.rest.rest.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(path = "/all-users")
    public List<Users> getUsers(){
        return  this.userService.getUsers();
    }

    @PostMapping(path = "/create-user")
    public Users createUser (@RequestBody Users userEntity){
        return this.userService.createUser(userEntity);
    }

    @DeleteMapping(path = "/delete-user/{userId}")
    public Boolean deleteUser(@PathVariable("userId") Long id){
        return this.userService.deleteUser(id);
    }

    @PutMapping(path = "/update-user/{userId}")
    public Users updateUser(
            @PathVariable("userId") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ){
        return this.userService.updateUser(id, email, name);
    }

}
