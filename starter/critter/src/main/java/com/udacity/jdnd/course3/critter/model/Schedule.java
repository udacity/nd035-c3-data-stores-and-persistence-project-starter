package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.enums.EmployeeSkill;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schedule")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(targetEntity = Employee.class)
    private List<Employee> employeeIds;

    @ManyToMany(targetEntity = Pet.class)
    private List<Pet> petIds;

    private LocalDate date;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "schedule_activities")
    @Enumerated(EnumType.STRING)
    @Column(name = "activities", nullable = false)
    private Set<EmployeeSkill> activities;
}
