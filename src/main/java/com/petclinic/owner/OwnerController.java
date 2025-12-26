package com.petclinic.owner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OwnerController {

    @GetMapping("/owners/find")
    public String initFindForm() {
        return "owners/findOwner";
    }
}
