package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule saveSchedule(Schedule schedule);
    List<Schedule> getAllSchedules();
    List<Schedule> getSchedulesByPetId(Long petId);
    List<Schedule> getSchedulesByEmployee(Long employeeId);
    List<Schedule> getSchedulesByCustomer(Long customerId);
}
