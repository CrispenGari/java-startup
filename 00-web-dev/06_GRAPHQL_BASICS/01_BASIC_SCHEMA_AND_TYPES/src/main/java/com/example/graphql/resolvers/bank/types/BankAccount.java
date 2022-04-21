package com.example.graphql.resolvers.bank.types;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BankAccount {
    private String name;
    private Client client;
    private Currency currency;
}
