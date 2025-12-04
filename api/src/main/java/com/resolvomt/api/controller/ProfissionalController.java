package com.resolvomt.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resolvomt.api.model.Profissional;

@RestController
@RequestMapping("/profissionais")
@CrossOrigin(origins = "*")
public class ProfissionalController {
    
    @GetMapping
    public List<Profissional> Listar() {

        Profissional p1 = new Profissional("Lucas Java", "Programador", 150.0, "Cuiabá");
        p1.setId((1L));

        Profissional p2 = new Profissional("Alice Veterinária", "Veterinária", 200.0, "Cuiabá");
        p2.setId(2L);
        return Arrays.asList(p1, p2);
    }
}
