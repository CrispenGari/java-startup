package com.example.graphql.food;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FoodService implements FoodServiceInterface{

    private  final  FoodRepository repository;
    @Override
    @GraphQLQuery(name = "foods", description = "getting all the foods")
    public Collection<Food> getFoods() {
        return this.repository.findAll();
    }

    @Override
    @GraphQLMutation(name = "save food" , description = "saving a food to the database")
    public Food addFood(Food food) {
        return this.repository.save(food);
    }

    @Override
    @GraphQLQuery(name = "food", description = "getting a food by id")
    public Food getFoodById(Long id) {
        return this.repository.getById(id);
    }

    @Override
    public Food updateFood(Food food) {
        return null;
    }
    @Override
    public Boolean deleteFood(Long id) {
        return null;
    }
}
