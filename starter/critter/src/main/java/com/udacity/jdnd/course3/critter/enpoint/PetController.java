package com.udacity.jdnd.course3.critter.enpoint;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;
    private final CustomerService customerService;
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Customer customer = new Customer();
        if (petDTO.getOwnerId() != 0) {
            customer = customerService.getCustomer(petDTO.getOwnerId());
        }

        Pet pet = convertPetDTOToEntity(petDTO);
        pet.setOwner(customer);
        Pet savedPet = petService.save(pet);
        if (customer!= null) {
            customer.addPet(savedPet);
        }
        return convertPetEntityToDTO(savedPet);
    }

    private PetDTO convertPetEntityToDTO(Pet savedPet) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(savedPet, PetDTO.class);
    }

    private Pet convertPetDTOToEntity(PetDTO petDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(petDTO, Pet.class);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertPetEntityToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petsDTO = new ArrayList<>();
        List<Pet> pets = petService.getAllPets();
        pets.forEach(pet -> {
            petsDTO.add(convertPetEntityToDTO(pet));
        });
        return petsDTO;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petsDTO = new ArrayList<>();
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        pets.forEach(pet -> {
            petsDTO.add(convertPetEntityToDTO(pet));
        });
        return petsDTO;
    }
}
