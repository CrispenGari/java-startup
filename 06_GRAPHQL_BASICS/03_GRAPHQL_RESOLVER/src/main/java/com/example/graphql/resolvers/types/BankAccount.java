package com.example.graphql.resolvers.types;

import lombok.*;

@Value
@Builder
public class BankAccount {
    private Currency currency;
    private String name;
    private Client client;
}
