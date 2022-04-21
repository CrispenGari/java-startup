package com.example.graphql.resolvers.bank.types;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BankAccountInput {
   private String name;
   private Client client;
   private String currency;
}
