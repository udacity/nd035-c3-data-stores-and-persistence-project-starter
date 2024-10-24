package com.udacity.jdnd.course3.critter.mapper;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.dto.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        Customer customer = new Customer();

        customer.setId(dto.getId());
        customer.setName(dto.getName() != null ? dto.getName() : "");
        customer.setPhoneNumber(dto.getPhoneNumber() != null ? dto.getPhoneNumber() : "");
        customer.setNotes(dto.getNotes() != null ? dto.getNotes() : "");

        if (dto.getPetIds() != null && !dto.getPetIds().isEmpty()) {
            List<Pet> pets = dto.getPetIds().stream()
                    .filter(Objects::nonNull)
                    .map(petId -> {
                        Pet pet = new Pet();
                        pet.setId(petId);
                        return pet;
                    })
                    .collect(Collectors.toList());
            customer.setPets(pets);
        } else {
            customer.setPets(new ArrayList<>());
        }

        return customer;
    }

    public CustomerDTO toDto(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setNotes(customer.getNotes());
        List<Long> petIds = customer.getPets().stream()
                .map(Pet::getId)
                .collect(Collectors.toList());
        dto.setPetIds(petIds);
        return dto;
    }
}
