package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.enums.PetType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pet")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PetType type;

    @Nationalized
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer owner;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String notes;
}
