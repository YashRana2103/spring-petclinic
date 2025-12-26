package com.petclinic.vet;

import com.petclinic.model.NamedEntity;
import com.petclinic.model.Person;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "vets")
public class Vet extends Person {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vet_specialties",
            joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    private Set<Specialty> specialties;

    protected Set<Specialty> getSpecialtiesInternal() {
        if (this.specialties == null) {
            this.specialties = new HashSet<>();
        }
        return this.specialties;
    }

    @XmlElement
    public List<Specialty> getSpecialties() {
        return getSpecialtiesInternal().stream()
                .sorted(Comparator.comparing(NamedEntity::getName))
                .collect(Collectors.toList());
    }

    public int getNrOfSpecialties() { return getSpecialtiesInternal().size(); }

    public void addSpecialty(Specialty specialty) {
        getSpecialtiesInternal().add(specialty);
    }
}
