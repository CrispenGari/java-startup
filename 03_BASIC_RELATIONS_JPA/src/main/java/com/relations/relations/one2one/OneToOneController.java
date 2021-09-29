package com.relations.relations.one2one;

import com.relations.relations.one2one.profile.Profile;
import com.relations.relations.one2one.user.User;
import com.relations.relations.one2one.user.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-profile")
public class OneToOneController {
    private final UserService service;
    @PostMapping(path = "/user-create")
    public ResponseEntity<User>  createUser(@RequestBody Body body){
        User user= new User();
        user.setProfile(body.getProfile());
        user.setUsername(body.getUser().getUsername());
        System.out.println(user);
        return ResponseEntity.status(201).body(this.service.createUser(user));
    }
}

@Data
class Body {
    private User user;
    private Profile profile;
}
