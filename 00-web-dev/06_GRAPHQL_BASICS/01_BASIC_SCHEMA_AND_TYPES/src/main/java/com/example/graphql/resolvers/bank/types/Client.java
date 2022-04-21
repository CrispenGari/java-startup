package com.example.graphql.resolvers.bank.types;
import lombok.*;

@Value
@Builder
public class Client {
    private String firstName;
    private String lastName;
    private double balance;
}
