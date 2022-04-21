package com.errors.errors.animal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "api/v1/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService service;

    @PostMapping(path = "/create")
    public ResponseEntity<Animal> createAnimal(@RequestBody Animal animal){
        return ResponseEntity.status(201).body(this.service.addAnimal(animal));
    }


    @PutMapping("/update/{animalId}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable("animalId") Long animalId, @RequestBody Animal animal){
        Animal animal1 = this.service.getAnimal(animalId);
        animal1.setName(animal.getName());
        return ResponseEntity.status(204).body(this.service.updateAnimal(animal1));
    }

    @DeleteMapping("/delete/{animalId}")
    public ResponseEntity<Boolean> deleteAnimal(@PathVariable("animalId") Long animalId){
        return ResponseEntity.status(204).body(this.service.deleteAnimal(animalId));
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<Animal> getAnimal(@PathVariable("animalId") Long animalId){
        return ResponseEntity.status(200).body(this.service.getAnimal(animalId));
    }

    @GetMapping("/all")
    public ResponseEntity<Collection<Animal>> getAnimals(){
        return ResponseEntity.status(200).body(this.service.getAnimals());
    }
}
