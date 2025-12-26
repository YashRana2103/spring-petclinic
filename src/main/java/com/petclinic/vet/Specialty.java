package com.petclinic.vet;

import com.petclinic.model.NamedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "specialties")
public class Specialty extends NamedEntity {
}
