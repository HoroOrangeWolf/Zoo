package com.minner.michalski.mozdzierz.ozga.zoo.Map;

import com.minner.michalski.mozdzierz.ozga.zoo.Animal.Section;
import com.minner.michalski.mozdzierz.ozga.zoo.Animal.SectionRepository;
import com.minner.michalski.mozdzierz.ozga.zoo.Test.TestPrint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        sectionRepository.deleteAll();
    }

    @Test
    public void addPath(){

        //given

        //when


        Path path = new Path();

        service.addPath(path);

        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        Section section2 = new Section("Ptaki", "Papuga ",
                false, 1.f, 1.f, "brak");

        PathElement pathElement = new PathElement(section, path,false);

        PathElement pathElement2 = new PathElement(section2, path,false);
        List<PathElement> pathElement1 = List.of(pathElement, pathElement2);

        sectionRepository.save(section);
        sectionRepository.save(section2);

        repository.save(pathElement);
        repository.save(pathElement2);

        //then
        List<Path> all = pathRepository.findAll();

        assertTrue(all.contains(path));

        Path path1 = all.get(0);

        assertEquals(path1.getPathElements().size(), pathElement1.size());
    }



}
