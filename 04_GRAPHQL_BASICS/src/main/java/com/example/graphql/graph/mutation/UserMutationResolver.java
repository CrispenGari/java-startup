package com.example.graphql.graph.mutation;

//import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.graphql.user.User;
import com.example.graphql.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserMutationResolver  {
    private final UserService userService;
    public User createUser(String username){
        User user = new User(username);
        return this.userService.createUser(user);
    }

    public Collection<User> findUsers(){
        return this.userService.findUsers();
    }

    public User findUser(Long id){
        return  this.userService.findUser(id);
    }
}
