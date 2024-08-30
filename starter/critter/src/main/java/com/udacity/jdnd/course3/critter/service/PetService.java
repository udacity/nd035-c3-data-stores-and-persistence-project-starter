package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Pet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PetService {
    Pet save(Pet pet);
    Pet findPetById(Long petId);
    List<Pet> getAllPets();
    List<Pet> getPetsByOwner(long ownerId);
    Pet getPetById(long petId);
}
