package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EmployeeService {
    public Employee saveEmployee(Employee employee);
    public Employee getEmployee(long employeeId);
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId);
    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date);
}
