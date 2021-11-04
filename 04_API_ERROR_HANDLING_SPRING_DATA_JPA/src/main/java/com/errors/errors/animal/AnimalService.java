package com.errors.errors.animal;

import com.errors.errors.exceptions.AnimalNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimalService implements AnimalServiceInterface{

    private final AnimalRepository repository;
    @Override
    public Animal addAnimal(Animal animal) {
        return this.repository.save(animal);
    }

    @Override
    public Animal getAnimal(Long id) {
        return this.repository.findById(id).orElseThrow(()-> new AnimalNotFoundException("the animal with id: " + id + "was not found"));
    }

    @Override
    public Boolean deleteAnimal(Long id) {
        this.repository.deleteById(id);
        return true;
    }

    @Override
    public Animal updateAnimal(Animal animal) {
        return this.repository.save(animal);
    }

    @Override
    public Collection<Animal> getAnimals() {
        return this.repository.findAll();
    }
}
