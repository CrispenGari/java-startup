package com.example.graphql.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class HelloResolver implements GraphQLQueryResolver {
    public String hello(String name){
        return "Hello, " + name;
    }
}
