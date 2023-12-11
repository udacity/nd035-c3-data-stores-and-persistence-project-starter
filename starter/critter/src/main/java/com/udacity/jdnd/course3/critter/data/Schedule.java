package com.udacity.jdnd.course3.critter.data;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Schedule {

  @Id @GeneratedValue private long id;

  private LocalDate date;

  @ManyToMany
  @JoinTable(
      name = "schedule_employee",
      joinColumns = @JoinColumn(name = "schedule_id"),
      inverseJoinColumns = @JoinColumn(name = "employee_id"))
  private List<Employee> employees;

  @ManyToMany
  @JoinTable(
      name = "schedule_pet",
      joinColumns = @JoinColumn(name = "schedule_id"),
      inverseJoinColumns = @JoinColumn(name = "pet_id"))
  private List<Pet> pets;

  @ElementCollection(targetClass = EmployeeSkill.class)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "schedule_activities", joinColumns = @JoinColumn(name = "schedule_id"))
  @Column(name = "activity")
  private Set<EmployeeSkill> activities;
}
