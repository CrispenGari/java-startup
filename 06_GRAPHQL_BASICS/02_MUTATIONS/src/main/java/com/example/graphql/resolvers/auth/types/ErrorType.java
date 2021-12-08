package com.example.graphql.resolvers.auth.types;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorType {
    private String field;
    private String message;
}
