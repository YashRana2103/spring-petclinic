package com.petclinic.vet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class VetController {

    private final VetRepository vets;

    public VetController(VetRepository clinicService) {
        this.vets = clinicService;
    }

    @GetMapping("/vets.html")
    public String showVetList(Model model){
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vets.findAll());
        model.addAttribute("vets", vets);
        return "vets/vetList";
    }

    /**
     *  adding all Veterinarians from to db into the {@code List<Vet>} of object {@link Vets}
     *  @return An JSON response body with {@code vetList: List<Vet>}
     */
    @GetMapping("/vets")
    public @ResponseBody Vets showResourcesVetList() {
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vets.findAll());
        return vets;
    }
}
