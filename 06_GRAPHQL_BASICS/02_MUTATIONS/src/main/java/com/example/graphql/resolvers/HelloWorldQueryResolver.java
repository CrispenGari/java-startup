package com.example.graphql.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;


@Component
public class HelloWorldQueryResolver implements GraphQLQueryResolver {
    public String hello(){
        return "hello world";
    }
}
