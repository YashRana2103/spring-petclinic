package com.petclinic.owner;

import com.petclinic.model.NamedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "types")
public class PetType extends NamedEntity {
}
