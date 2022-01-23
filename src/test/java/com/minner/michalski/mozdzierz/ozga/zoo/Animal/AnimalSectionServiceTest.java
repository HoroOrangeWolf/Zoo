package com.minner.michalski.mozdzierz.ozga.zoo.Animal;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
public class AnimalSectionServiceTest {
    @Autowired
    public AnimalSectionService service;

    @Autowired
    public AnimalRepository animalRepository;

    @Autowired
    public SectionRepository sectionRepository;

    @AfterEach
    public void clear(){
        animalRepository.deleteAll();
        sectionRepository.deleteAll();
    }

    @Test
    public void addSection(){
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        //when
        service.addSection(section);

        //then
        List<Section> all = sectionRepository.findAll();

        assertTrue(all.contains(section));
    }

    @Test
    public void removeSection(){
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        //when
        sectionRepository.save(section);

        service.removeSection(section.getId());

        //then
        List<Section> all = sectionRepository.findAll();

        assertFalse(all.contains(section));
    }

    @Test
    public void updateSection(){
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        Section toUpdate = new Section("Orangutany", "Tu są s ",
                true, 1.f, 1.f, "nie ma");

        //when
        sectionRepository.save(section);

        toUpdate.setId(section.getId());

        service.updateSection(toUpdate);

        //then
        Optional<Section> byId = sectionRepository.findById(section.getId());
        assertTrue(byId.isPresent());

        assertEquals(section, toUpdate);
    }

    @Test
    public void addAnimal(){
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        Animal animal = new Animal("Orangutan", "Taka brązowa małpka", section);

        //when
        sectionRepository.save(section);

        service.addAnimal(animal);


        //then
        List<Animal> all = animalRepository.findAll();

        assertTrue(all.contains(animal));
    }

    @Test
    public void removeAnimal(){
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        Animal animal = new Animal("Orangutan", "Taka brązowa małpka", section);

        //when
        sectionRepository.save(section);

        animalRepository.save(animal);

        service.removeAnimal(animal.getId());

        //then
        List<Animal> all = animalRepository.findAll();

        assertFalse(all.contains(animal));
    }

    @Test
    public void updateAnimal(){
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        Animal animal = new Animal("Orangutan", "Taka brązowa małpka", section);

        Animal toUpdate = new Animal("Nie Orangutan", "Taka brązowa małpka już nie", section);

        //when
        sectionRepository.save(section);

        animalRepository.save(animal);

        toUpdate.setId(animal.getId());

        service.updateAnimal(toUpdate);

        //then
        Optional<Animal> byId = animalRepository.findById(animal.getId());
        assertTrue(byId.isPresent());

        assertEquals(animal, toUpdate);
    }

    @Test
    public void getAnimal(){
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        Animal animal = new Animal("Orangutan", "Taka brązowa małpka", section);

        //when
        sectionRepository.save(section);

        animalRepository.save(animal);

        Animal animal1 = service.getAnimal(animal.getId());

        //then
        assertEquals(animal1, animal);
    }

    @Test
    public void getSection(){
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");


        //when
        sectionRepository.save(section);


        Section section1 = service.getSection(section.getId());

        //then
        assertEquals(section, section1);
    }

    @Test
    public void getAllSections(){
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        Section section2 = new Section("Ptaki", "Papuga ",
                true, 1.f, 1.f, "brak");

        //when
        sectionRepository.save(section);
        sectionRepository.save(section2);

        Section[] sections = service.getAllSections().toArray(new Section[0]);

        //then

        assertArrayEquals(sections, new Section[]{section, section2});
    }

    @Test
    public void getAllAnimals(){
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");


        Animal animal = new Animal("Orangutan", "Taka brązowa małpka", section);
        Animal animal2 = new Animal("Pieski fajne", "fajny piesek", section);
        //when
        sectionRepository.save(section);

        animalRepository.save(animal);
        animalRepository.save(animal2);

        Animal[] animals = service.getAllAnimals().toArray(new Animal[0]);

        //then

        assertArrayEquals(animals, new Animal[]{animal, animal2});
    }

    @Test
    public void getAllSectionsOnMap(){
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        Section section2 = new Section("Ptaki", "Papuga ",
                false, 1.f, 1.f, "brak");

        //when
        sectionRepository.save(section);
        sectionRepository.save(section2);

        Section[] sections = service.getSectionsOnMap().toArray(new Section[0]);

        //then

        assertArrayEquals(sections, new Section[]{section});
    }

}
