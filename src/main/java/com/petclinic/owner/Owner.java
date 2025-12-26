package com.petclinic.owner;

import com.petclinic.model.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "owners")
public class Owner extends Person {

    @Column(name = "address")
    @NotBlank
    private String address;

    @Column(name = "city")
    @NotBlank
    private String city;

    @Column(name = "telephone")
    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "{telephone_invalid}")
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    @OrderBy("name")
    private final List<Pet> pets = new ArrayList<>();

    public void addPet(Pet pet) {
        if (pet.isNew()) {
            this.getPets().add(pet);
        }
    }

    public Pet getPet(String name) {
        return this.getPet(name, true);
    }

    public Pet getPet(Integer id){
        for (Pet pet: this.getPets()) {
            if (!pet.isNew()) {
                Integer compId = pet.getId();
                if (Objects.equals(compId, id)) {
                    return pet;
                }
            }
        }
        return null;
    }

    private Pet getPet(String name, boolean includeNew) {
        for (Pet pet: this.getPets()) {
            String compName = pet.getName();
            if (compName != null && compName.equalsIgnoreCase(name)) {
                if (includeNew || !pet.isNew()) {
                    return pet;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("new", this.isNew())
                .append("lastname", this.getLastName())
                .append("firstname", this.getFirstName())
                .append("address", this.getAddress())
                .append("city", this.getCity())
                .append("telephone", this.getTelephone())
                .toString();
    }

    public void addVisit(Integer petId, Visit visit) {

        Assert.notNull(petId, "Pet ID must not be null!");
        Assert.notNull(visit, "Visit must not be null!");

        Pet pet = this.getPet(petId);

        Assert.notNull(pet, "Invalid Pet ID!");

        pet.addVisit(visit);
    }
}
