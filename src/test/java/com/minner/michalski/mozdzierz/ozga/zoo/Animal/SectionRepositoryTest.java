package com.minner.michalski.mozdzierz.ozga.zoo.Animal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
public class SectionRepositoryTest {

    @Autowired
    public SectionRepository repository;

    @AfterEach
    public void clear(){
        repository.deleteAll();
    }

    @Test
    public void getAllSectionOnMap(){
        //given
        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        Section section2 = new Section("Ptaki", "Papuga ",
                false, 1.f, 1.f, "brak");

        //when
        repository.save(section);
        repository.save(section2);

        Section[] sections = repository.getAllSectionsOnMap().toArray(new Section[0]);

        //then

        assertArrayEquals(sections, new Section[]{section});
    }

}
