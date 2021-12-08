package com.example.graphql.resolvers.auth.types;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthResponse {
    private User user;
    private ErrorType error;
}
