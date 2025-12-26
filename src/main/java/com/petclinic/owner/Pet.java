package com.petclinic.owner;

import com.petclinic.model.NamedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PetType type;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    @OrderBy("date ASC")
    private final Set<Visit> visits = new LinkedHashSet<>();

    public Collection<Visit> getVisits() {
        return this.visits;
    }

    public void addVisit(Visit visit) {
        this.getVisits().add(visit);
    }

}
