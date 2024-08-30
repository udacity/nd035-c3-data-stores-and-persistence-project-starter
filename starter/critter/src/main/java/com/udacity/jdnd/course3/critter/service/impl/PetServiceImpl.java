package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.exception.ResponseNotFoundException;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;
    @Override
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Pet findPetById(Long petId) {
        return petRepository.findById(petId).orElse(null);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> getPetsByOwner(long ownerId) {
        return petRepository.findPetsByOwnerId(ownerId);
    }

    @Override
    public Pet getPetById(long petId) {
        return petRepository.findById(petId).orElseThrow(ResponseNotFoundException::new);
    }
}
