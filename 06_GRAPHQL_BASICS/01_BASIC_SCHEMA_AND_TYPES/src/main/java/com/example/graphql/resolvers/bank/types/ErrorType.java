package com.example.graphql.resolvers.bank.types;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ErrorType {
    private  String field;
    private String message;
}
