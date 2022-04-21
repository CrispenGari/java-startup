package com.example.graphql.resolvers.types;

import lombok.*;

@Value
@Builder
public class Client {
    private String firstName;
    private String lastName;
}
