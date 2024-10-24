package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.mapper.PetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository; // Repositorio para obtener Customer

    @Autowired
    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = PetMapper.toEntity(petDTO);

        // Obtener el Customer asociado
        Customer customer = customerRepository.findById(petDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        pet.setCustomer(customer);
        Pet savedPet = petRepository.save(pet);

        List<Pet> pets = new ArrayList<>();
        pets.add(savedPet);
        customer.setPets(pets);
        customerRepository.save(customer);

        return PetMapper.toDTO(savedPet);
    }

    public PetDTO getPet(long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        return PetMapper.toDTO(pet);
    }

    public List<PetDTO> getPets() {
        return petRepository.findAll().stream()
                .map(PetMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PetDTO> getPetsByOwner(long customerId) {
        return petRepository.findByCustomerId(customerId).stream()
                .map(PetMapper::toDTO)
                .collect(Collectors.toList());
    }
}
