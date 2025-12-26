package com.petclinic;

import com.petclinic.model.BaseEntity;
import com.petclinic.model.Person;
import com.petclinic.vet.Vet;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class PetClinicRuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.resources().registerPattern("db/*");
        hints.resources().registerPattern("messages/*");
        hints.serialization().registerType(BaseEntity.class);
        hints.serialization().registerType(Person.class);
        hints.serialization().registerType(Vet.class);
    }
}
