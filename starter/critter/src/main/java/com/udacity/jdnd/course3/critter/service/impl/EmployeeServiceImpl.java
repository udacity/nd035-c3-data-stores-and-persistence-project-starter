package com.udacity.jdnd.course3.critter.service.impl;

import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployee(long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @Override
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        }
    }

    @Override
    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
        List<Employee> availableEmployees = new ArrayList<>();
        List<Employee> employees = employeeRepository.findEmployeesByDaysAvailable(date.getDayOfWeek());
        for (Employee employee : employees) {
            if (employee.getSkills().containsAll(skills)) {
                availableEmployees.add(employee);
            }
        }
        return availableEmployees;
    }
}
