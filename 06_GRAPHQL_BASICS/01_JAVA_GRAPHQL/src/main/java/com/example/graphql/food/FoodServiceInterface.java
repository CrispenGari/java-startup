package com.example.graphql.food;

import io.leangen.graphql.annotations.GraphQLArgument;

import java.util.Collection;
import java.util.Optional;

public interface FoodServiceInterface {
    Collection<Food> getFoods();
    Food addFood(@GraphQLArgument(name = "food") Food food);
    Food getFoodById(Long id);
    Food updateFood(Food food);
    Boolean deleteFood(Long id);
}
