package com.example.graphql.resolvers.bank;
import com.example.graphql.resolvers.bank.types.*;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
public class BankAccountResolver implements GraphQLQueryResolver{
    public BankAccountResponse bankAccount(BankAccountInput input){
        Client client = Client.builder()
                .balance(input.getClient().getBalance())
                .firstName(input.getClient().getFirstName())
                .lastName(input.getClient().getLastName())
                .build();
        Currency currency = input.getCurrency()
                .toLowerCase(Locale.ROOT) == "zar" ? Currency.ZAR : Currency.USD;

        BankAccount b = BankAccount.builder()
                .name(input.getName())
                .client(client)
                .currency(currency)
                .build();

        return  BankAccountResponse.builder()
                .account(b)
                .build();
    }
}
