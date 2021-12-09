package com.example.graphql.resolvers.types;


import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ClientInput {

        private String firstName;
        private String lastName;
}


