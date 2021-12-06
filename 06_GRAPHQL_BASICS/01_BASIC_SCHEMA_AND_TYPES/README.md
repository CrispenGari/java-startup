### 01: Basic Schema and Types

In this one we are going to do a step by step implementation of a graphql api that will give us the following response:

```json
{
    "data": {
        "bankAccount": {
            "error": null,
            "account": {
                "name": "bank-001",
                "currency": "USD",
                "client": {
                    "firstName": "firstName",
                    "lastName": "lastName",
                    "balance": 234.99
                }
            }
        }
    }
}
```

When when we send the following request to the graphql server:

```
query BankAccount($input: BankAccountInput!){
    bankAccount(input: $input){
        error{
            field
            message
        }
        account{
            name
            currency
            client{
                firstName,
                lastName,
                balance
            }

        }
    }
}
```

With the following graphql variables:

```java
{
    "input": {
        "name": "bank-001",
        "client":{
            "firstName": "firstName",
            "balance": 234.99,
            "lastName": "lastName"
        },
         "currency": "zar"
    }
}
```

Essentially we are going to make use of the following basic graphql types:
1. Enum
2. Input Types
3. Float, String
4. Object types

> _Note that there are more types that we wont look at in this example such as `Array` type but we will look at them
letter on when we try to implement the `mutation` type.


First we need to create our `schema`. We are going to create a file called `Query.graphqls` in the properties/graphql folder
and it will look as follows:

```graphql
type Query{
    hello(name: String): String!
    bankAccount(input: BankAccountInput!) : BankAccountResponse
}
```
Here we are intrested in the `bankAccount` query which takes in a bank account input `BankAccountInput`
which is found in the `inputs/inputs.graphqls` and looks as follow:

```
# input type for a client account
input ClientInput{
    firstName: String!
    lastName: String!
    balance: Float!
}
# input type for a bank account
input BankAccountInput{
    client: ClientInput!
    name: String!
    currency: String!
}
```
> Note that all the graphql files has to have an extension `.graphqls` so that `graphql-java` will pick them up
in our spring boot application when building the `schema`.

### ClientInput
Client input is a java class that is located in the `resolvers/bank/types` package in a class `ClientInput` which looks as follows:

```java
@Value
@Builder
public class ClientInput{
    private String firstName;
    private String lastName;
    private double balance;
}
```
### BankAccountInput
A bankAccount input is also a java class `BankAccountInput` which looks as follows:

```java
@Value
@Builder
public class BankAccountInput {
   private String name;
   private Client client;
   private String currency;
}
```
### BankAccountResolver
The `BankAccountInput` is passed down to the `BankAccountResolver` which is located in the `bank` package and it
looks as follows:

```java
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

        return BankAccountResponse.builder()
                .account(b)
                .build();
    }
}
```
The `BankAccountResolver` implements the `GraphQLQueryResolver` which allows us to match the `Query` `bankAccount` in our
schema `Query`.
```
bankAccount(input: BankAccountInput!) : BankAccountResponse
```

### BankAccountResponse
The bank account response is an object type which looks as follows:

```java
@Value
@Builder
public class BankAccountResponse {
    private  ErrorType error;
    private BankAccount account;
}
```
It has either two things:
1. ErrorType
ErrorType is nothing but a java class which is in the types and looks as follows:

```java
@Builder
@Value
public class ErrorType {
    private  String field;
    private String message;
}

```

The schema definition for the error object type looks as follows:
```
type  Error{
    message: String!
    field: String!
}
```
2. BankAccount
A bank account type will be something that looks as follows:

```java
@Value
@Builder
public class BankAccount {
    private String name;
    private Client client;
    private Currency currency;
}
```
The `schema` definition for the `BankAccount` looks as follows:

```
type BankAccount{
    client: Client!
    currency: Currency!
    name: String!
}
```

### Client type

The client type looks as follows:

```
type Client{
    firstName: String!
    lastName: String!
    balance: Float!
}
```

### Currency Enum type
The currency is a graphql enum type with two values `ZAR` and `USD` and it looks as follows:

```
enum Currency{
   USD,
    ZAR
}
```
### BankAccountResponse
The `BankAccountResponse` schema definition if found in the `graphql/object/objectTypes.graphqls` file and
it looks as follows:

```
# Error Type

type  Error{
    message: String!
    field: String!
}
# Bank account Response
type BankAccountResponse{
    error: Error
    account: BankAccount
}
```


### Next

Next we will look at the `Mutation` type.