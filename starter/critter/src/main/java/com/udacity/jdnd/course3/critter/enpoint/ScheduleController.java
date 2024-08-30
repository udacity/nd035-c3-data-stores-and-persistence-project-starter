package com.udacity.jdnd.course3.critter.enpoint;

import com.udacity.jdnd.course3.critter.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.exception.ResponseNotFoundException;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final PetService petService;
    private final EmployeeService employeeService;
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertEntityToScheduleDTO(scheduleService.saveSchedule(convertScheduleDTOToEntity(scheduleDTO)));
    }

    private Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        scheduleDTO.setActivities(scheduleDTO.getActivities());
        Map<Long, Employee> employeeMap = new HashMap<>();
        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            Optional<Employee> optionalEmployee = Optional.ofNullable(employeeService.getEmployee(employeeId));
            if (!optionalEmployee.isPresent()) {
                throw new ResponseNotFoundException("Employee not found");
            } else {
                employeeMap.put(employeeId, optionalEmployee.get());
            }
        }
        schedule.setEmployeeIds(new ArrayList<>(employeeMap.values()));
        Map<Long, Pet> petMap = new HashMap<>();
        for (Long petId : scheduleDTO.getPetIds()) {
            Optional<Pet> optionalPet = Optional.ofNullable(petService.getPetById(petId));
            if (!optionalPet.isPresent()) {
                throw new ResponseNotFoundException("Pet not found");
            } else {
                petMap.put(petId, optionalPet.get());
            }
        }
        schedule.setPetIds(new ArrayList<>(petMap.values()));
        return schedule;
    }

    private ScheduleDTO convertEntityToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setActivities(schedule.getActivities());
        List<Pet> pets = schedule.getPetIds();
        List<Long> petIds = new ArrayList<>();
        for (Pet pet : pets) {
            petIds.add(pet.getId());
        }
        scheduleDTO.setPetIds(petIds);
        List<Employee> employees = schedule.getEmployeeIds();
        List<Long> employeeIds = new ArrayList<>();
        for (Employee employee : employees) {
            employeeIds.add(employee.getId());
        }
        scheduleDTO.setEmployeeIds(employeeIds);
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convertEntityToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getSchedulesByPetId(petId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convertEntityToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getSchedulesByEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convertEntityToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getSchedulesByCustomer(customerId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOS.add(convertEntityToScheduleDTO(schedule));
        }
        return scheduleDTOS;
    }
}
