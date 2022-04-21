### GraphQL Resolver

In this one we are going to look at the `GraphQLResolver` interface in `graphql-java` and when to use it
in substitution with the `GraphQLQueryResolver` and `GraphQLMutationResolver`.

Suppose we have a `schema` that looks as follows:

```


# client type
type Client{
    firstName: String!
    lastNames: String!
}

# currency type
enum Currency{
    USD,
    ZAR
}

# bank account type
type BankAccount{
    name: String!,
    client: Client!
    currency: Currency!
}

# client input type
input ClientInput{
    firstName: String!
    lastName: String
}

# bank account input type
input BankAccountInput{
    client: ClientInput!,
    currency: String!,
    name: String!
}
type Query{
    bankAccount(
        input: BankAccountInput
    ): BankAccount
}
```

Our `BankAccount` resolver will look something like in the `resolver` package:

```java
@Component
public class BankAccountResolver implements GraphQLQueryResolver {
    public BankAccount bankAccount(BankAccountInput input){
        Client client = Client.builder()
                .firstName(input.getClient().getFirstName()).lastName(input.getClient().getLastName()).build();
        return  BankAccount.builder()
                .client(client)
                .currency(input.getCurrency().toLowerCase(Locale.ROOT) == "zar" ? Currency.ZAR : Currency.USD)
                .name(input.getName())
                .build();
    }
}
```

Suppose we want to get the data of the client, from our query we can easily get it by sending the `graphql` request

```
query BankAccount($input: BankAccountInput!){
    bankAccount(input: $input){
        name
        currency
        client{
            firstName
            lastName
        }
    }
}
```

With the following graphql variables:

```json
{
  "input":{
  "client":{
      "firstName": "fname",
      "lastName": "lname"
  },
  "currency": "zar",
  "name": "001"
}
}
```
To get the following graphql response:

```json
{
    "data": {
        "bankAccount": {
            "name": "001",
            "currency": "USD",
            "client": {
                "firstName": "fname",
                "lastName": "lname"
            }
        }
    }
}
```

This is a normal GraphQLQueryResolver on the bankAccount. Let's say we are getting the `client` from an external
API. We may want to use the `GraphQLResolver` on the client of `BankAccount`. Our `bankAccount` resolver will
look as follows:

```java
@Component
public class BankAccountResolver implements GraphQLQueryResolver {
    public BankAccount bankAccount(BankAccountInput input){
        return  BankAccount.builder()
                .currency(input.getCurrency().toLowerCase(Locale.ROOT).equals("zar") ? Currency.ZAR : Currency.USD)
                .name(input.getName())
                .build();
    }
}
```
With this code the `client` will be `null` and in our `schema` client is not null when resolving the
`bankAccount` so we need to create a new Java class that resolves the client `ClientResolver` and it looks as follows:

```java
@Component
public class ClientResolver implements GraphQLResolver<BankAccount> {
    public Client client(BankAccount account){
       //        get the data from the database
        return  Client.builder().firstName("client1").lastName("client").build();
    }
}
```
We will have the access to the `root` query the `bankAccount` and we will resolve the `client` using the
`GraphQLResolver<T>` which takes a type `T` in our case it is the BankAccount. And we will get exposed to
the `BankAccount` class we can access the fields of the bankAccount with `account.getName()`.


Now if we go to the playground we will be able to make queries like:

```
query BankAccount($input: BankAccountInput!){
    bankAccount(input: $input){
        name
        currency
        client{
            firstName
            lastName
        }
    }
}
```

With the following query variables:

```json
{
    "input":{
  "client":{
      "firstName": "fname",
      "lastName": "lname"
  },
  "currency": "zar",
  "name": "001"
}
}
```
To get the following response:

```json
{
    "data": {
        "bankAccount": {
            "name": "001",
            "currency": "USD",
            "client": {
                "firstName": "client1",
                "lastName": "client"
            }
        }
    }
}
```

Note that the firstName and lastName for the client are the one that we have hard-coded, most of the time we get this
data from the database or from an external api.

### Next

In the next one we are going to look at `exceptional` handling.