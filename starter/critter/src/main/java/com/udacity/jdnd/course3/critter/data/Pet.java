package com.udacity.jdnd.course3.critter.data;

import com.udacity.jdnd.course3.critter.pet.PetType;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Pet {

  @Id @GeneratedValue private long id;

  @Nationalized private String name;

  @Enumerated(EnumType.STRING)
  private PetType type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private Customer customer;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  private String notes;
}
