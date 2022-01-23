package com.minner.michalski.mozdzierz.ozga.zoo.Map;

import com.minner.michalski.mozdzierz.ozga.zoo.Animal.Section;
import com.minner.michalski.mozdzierz.ozga.zoo.Animal.SectionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
public class MapServiceTest {

    @Autowired
    public MapService service;

    @Autowired
    public PathRepository pathRepository;

    @Autowired
    public PathElementRepository repository;

    @Autowired
    public SectionRepository sectionRepository;

    @AfterEach
    public void clear(){
        pathRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    public void addPath(){

        //given
//        Section section = new Section("Pawiany", "Tu są małpki ",
//                true, 1.f, 1.f, "brak");
//
//        Section section2 = new Section("Ptaki", "Papuga ",
//                false, 1.f, 1.f, "brak");
//
//        PathElement pathElement = new PathElement(section, false);
//
//        PathElement pathElement2 = new PathElement(section2, false);
//
//        List<PathElement> pathElement1 = List.of(pathElement, pathElement2);

        Path path = new Path();

//        path.setPathElements(pathElement1);
        //when

//        sectionRepository.save(section);
//        sectionRepository.save(section2);
//
//        repository.save(pathElement);
//        repository.save(pathElement2);

        pathRepository.save(path);

        List<Path> all = pathRepository.findAll();

        //then
        assertTrue(all.contains(path));
    }



}
