package com.example.graphql.resolvers;

import com.example.graphql.resolvers.types.BankAccount;
import com.example.graphql.resolvers.types.BankAccountInput;
import com.example.graphql.resolvers.types.Client;
import com.example.graphql.resolvers.types.Currency;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BankAccountResolver implements GraphQLQueryResolver {
    public BankAccount bankAccount(BankAccountInput input){
        return  BankAccount.builder()
                .currency(input.getCurrency().toLowerCase(Locale.ROOT).equals("zar") ? Currency.ZAR : Currency.USD)
                .name(input.getName())
                .build();
    }
}
