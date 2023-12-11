package com.udacity.jdnd.course3.critter.data;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Customer {

  @Id @GeneratedValue private long id;

  @Nationalized private String name;

  private String phoneNumber;

  private String notes;

  @OneToMany(
      mappedBy = "customer",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      targetEntity = Pet.class)
  private List<Pet> pets;
}
