package com.minner.michalski.mozdzierz.ozga.zoo.Map;

import com.minner.michalski.mozdzierz.ozga.zoo.Animal.Section;
import com.minner.michalski.mozdzierz.ozga.zoo.Animal.SectionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @Test
    public void removePath(){

        //given

        Path path = new Path();

        pathRepository.save(path);

        //when

        service.removePath(path.getId());

        //then

        assertFalse(pathRepository.findById(path.getId()).isPresent());


    }

    @Test
    public void updatePath(){

        //given

        Path path = new Path();

        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        sectionRepository.save(section);

        pathRepository.save(path);

        PathElement pathElement = new PathElement(section, path, false);

        Path path_to = new Path(path.getId(), List.of(pathElement));

        //when

        service.updatePath(path_to);

        //then

        Optional<Path> byId = pathRepository.findById(path.getId());

        assertTrue(byId.isPresent());

        Path path1 = byId.get();

        assertEquals(1, path1.getPathElements().size());

    }

    @Test
    public void setIsVisited(){

        //given

        Path path = new Path();

        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        sectionRepository.save(section);

        pathRepository.save(path);

        PathElement pathElement = new PathElement(section, path, false);

        Path path_to = new Path(path.getId(), List.of(pathElement));

        pathRepository.save(path_to);

        Optional<Path> byId1 = pathRepository.findById(path.getId());

        assertTrue(byId1.isPresent());

        Path path2 = byId1.get();

        List<PathElement> pathElements = path2.getPathElements();

        assertTrue(pathElements.size() != 0);

        //when

        service.setVisited(pathElements.get(0).getId(), true);

        //then

        Optional<PathElement> byId = repository.findById(pathElements.get(0).getId());

        assertTrue(byId.isPresent());

        PathElement path1 = byId.get();

        assertTrue(path1.getIsVisited());

    }

    @Test
    public void setVisitedInvalidId(){
        //given
        Long invalidId = -1L;

        //then

        assertThatThrownBy(()->{
          service.setVisited(invalidId, true);
        }).isInstanceOf(IllegalStateException.class).hasMessage("Path element by id: " + invalidId + " is not existing!");

    }

    @Test
    public void getPath(){
        //given

        Path path = new Path();

        Section section = new Section("Pawiany", "Tu są małpki ",
                true, 1.f, 1.f, "brak");

        sectionRepository.save(section);

        pathRepository.save(path);

        PathElement pathElement = new PathElement(section, path, false);

        Path path_to = new Path(path.getId(), List.of(pathElement));

        pathRepository.save(path_to);

        Optional<Path> byId1 = pathRepository.findById(path.getId());

        assertTrue(byId1.isPresent());

        Path path2 = byId1.get();

        List<PathElement> pathElements = path2.getPathElements();

        assertTrue(pathElements.size() != 0);

        //when

        Path returned = service.getPath(path.getId());


        //then

        assertEquals(path2, returned);
    }

    @Test
    public void getPathInvalidId(){
        //given
        Long invalidId = -1L;

        //when

        assertThatThrownBy(()->{
            service.getPath(invalidId);
        }).isInstanceOf(IllegalStateException.class).hasMessage("Path by id: " + invalidId + " is not existing!");
    }


}
