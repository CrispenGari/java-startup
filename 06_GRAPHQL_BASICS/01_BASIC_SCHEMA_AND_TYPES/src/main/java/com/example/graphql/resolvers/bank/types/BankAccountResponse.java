package com.example.graphql.resolvers.bank.types;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BankAccountResponse {
    private  ErrorType error;
    private BankAccount account;
}
