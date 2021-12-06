package com.example.graphql.resolvers.bank.types;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientInput{
    private String firstName;
    private String lastName;
    private double balance;
}
