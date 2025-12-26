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

    /**
     * Handles HTML requests.
     * <ol><li><p> Fetches all vets and wraps them in a {@link Vets} object.
     * <li><p> Adds the wrapper to the Model for Thymeleaf (accessible via "vets"). Thymeleaf inspects the public getter {@code getVetList()} and generates
     *  the {@code vetList} based on the JavaBean convention.
     * </ol>
     * @return the view template "vets/vetList".
     */
    @GetMapping("/vets.html")
    public String showVetList(Model model) {
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vets.findAll());
        model.addAttribute("vets", vets);
        return "vets/vetList";
    }

    /**
     * Handles API requests (JSON/XML).
     * @return the {@link Vets} wrapper object directly.
     * Jackson inspects the public getter {@code getVetList()} and generates
     * the JSON key {@code "vetList": [...]} based on the JavaBean convention.
     */
    @GetMapping("/vets")
    public @ResponseBody Vets showResourcesVetList() {
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vets.findAll());
        return vets;
    }
}