package com.example.graphql.resolvers.types;


import lombok.*;


@Value
@Builder
public class BankAccountInput {
    private ClientInput client;
    private String name;
    private String currency;
}

