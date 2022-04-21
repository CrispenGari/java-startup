package com.example.graphql.resolvers.auth.types;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    private  String username;
    private String password;
}
