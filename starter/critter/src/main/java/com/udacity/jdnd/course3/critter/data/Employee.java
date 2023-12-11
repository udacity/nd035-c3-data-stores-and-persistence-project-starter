package com.udacity.jdnd.course3.critter.data;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import java.time.DayOfWeek;
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
import javax.persistence.ManyToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Employee {

  @Id @GeneratedValue private long id;

  @Nationalized private String name;

  @ElementCollection(targetClass = EmployeeSkill.class)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
  @Column(name = "skill", nullable = false)
  private Set<EmployeeSkill> activities;

  @ElementCollection(targetClass = DayOfWeek.class)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "employee_availability", joinColumns = @JoinColumn(name = "employee_id"))
  @Column(name = "day_of_week")
  private Set<DayOfWeek> availability;

  @ManyToMany(mappedBy = "employees")
  private Set<Schedule> schedules;
}
