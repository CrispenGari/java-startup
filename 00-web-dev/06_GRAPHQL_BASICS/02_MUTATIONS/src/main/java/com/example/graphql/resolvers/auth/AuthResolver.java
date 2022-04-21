package com.example.graphql.resolvers.auth;
import com.example.graphql.resolvers.auth.types.AuthResponse;
import com.example.graphql.resolvers.auth.types.ErrorType;
import com.example.graphql.resolvers.auth.types.User;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class AuthResolver implements GraphQLMutationResolver {

    public AuthResponse login(String username, String password){
        if(username.length() < 3){
            ErrorType error = ErrorType.builder()
                    .field("username").message("username must be at least 3 chars.").build();
            return AuthResponse.builder().error(error).build();
        }
        if(password.length() < 3){
            ErrorType error = ErrorType.builder()
                    .field("password").message("password must be at least 3 chars.").build();
            return AuthResponse.builder().error(error).build();
        }

        User user = User.builder().username(username).password(password).build();
        return AuthResponse.builder().user(user).build();
    }

    public AuthResponse register(String username, String password){
        if(username.length() < 3){
            ErrorType error = ErrorType.builder()
                    .field("username").message("username must be at least 3 chars.").build();
            return AuthResponse.builder().error(error).build();
        }
        if(password.length() < 3){
            ErrorType error = ErrorType.builder()
                    .field("password").message("password must be at least 3 chars.").build();
            return AuthResponse.builder().error(error).build();
        }

        User user = User.builder().username(username).password(password).build();
        return AuthResponse.builder().user(user).build();
    }
    public Boolean logout(){
        return true;
    }
}
