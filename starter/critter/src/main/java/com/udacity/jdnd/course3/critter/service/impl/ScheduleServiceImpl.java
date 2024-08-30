package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.exception.ResponseNotFoundException;
import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CustomerRepository customerRepository;


    @Override
    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getSchedulesByPetId(Long petId) {
        return scheduleRepository.getScheduleByPetIds_Id(petId);
    }

    @Override
    public List<Schedule> getSchedulesByEmployee(Long employeeId) {
        return scheduleRepository.getScheduleByEmployeeIds_Id(employeeId);
    }

    @Override
    public List<Schedule> getSchedulesByCustomer(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (!optionalCustomer.isPresent()) {
            throw new ResponseNotFoundException("Customer not found");
        } else {
            Customer customer = optionalCustomer.get();
            List<Pet> pets = customer.getPets();
            List<Schedule> schedules = new ArrayList<>();
            for (Pet pet : pets) {
                schedules.addAll(scheduleRepository.getScheduleByPetIds_Id(pet.getId()));
            }
            return schedules;
        }
    }
}
