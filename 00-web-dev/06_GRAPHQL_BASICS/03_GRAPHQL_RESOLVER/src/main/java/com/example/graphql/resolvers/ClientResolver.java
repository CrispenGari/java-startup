package com.example.graphql.resolvers;

import com.example.graphql.resolvers.types.BankAccount;
import com.example.graphql.resolvers.types.Client;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

@Component
public class ClientResolver implements GraphQLResolver<BankAccount> {
    public Client client(BankAccount account){
       //        get the data from the database
        return  Client.builder().firstName("client1").lastName("client").build();
    }
}
