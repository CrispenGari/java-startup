package com.example.graphql.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{

    private final UserRepository repository;
    @Override
    public User createUser(User user) {
        return this.repository.save(user);
    }

    @Override
    public Collection<User> findUsers() {
        return this.repository.findAll();
    }

    @Override
    public User findUser(Long id) {
        return this.repository.findById(id).orElseThrow(()-> new RuntimeException("no user with that id."));
    }
}
