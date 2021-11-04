package com.errors.errors.animal;

import java.util.Collection;

public interface AnimalServiceInterface {
    Animal addAnimal(Animal animal);
    Animal getAnimal(Long id);
    Boolean deleteAnimal(Long id);
    Animal updateAnimal(Animal animal);
    Collection<Animal> getAnimals();
}
