package com.example.graphql.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class HelloWorld implements GraphQLQueryResolver {

    public String hello(String name){
        return "my name is " + name;
    }
}
