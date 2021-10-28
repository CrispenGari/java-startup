package com.example.graphql.user;

import java.util.Collection;

public interface UserServiceInterface {
    User createUser(User user);
    Collection<User> findUsers();
    User findUser(Long id);
}
