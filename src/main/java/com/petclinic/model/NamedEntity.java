package com.petclinic.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class NamedEntity extends BaseEntity{

    @Column(name = "name")
    @NotBlank
    public String name;

    public String toString(){
        String name = this.name;
        return (name != null) ? name : "<null>";
    }
}
