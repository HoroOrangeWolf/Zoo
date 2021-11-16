package com.minner.michalski.mozdzierz.ozga.zoo.Animal;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/animal")
@AllArgsConstructor
public class AnimalController {

    private final AnimalSectionService service;


}
