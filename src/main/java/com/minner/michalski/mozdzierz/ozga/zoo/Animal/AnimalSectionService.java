package com.minner.michalski.mozdzierz.ozga.zoo.Animal;

import lombok.AllArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@AllArgsConstructor
public class AnimalSectionService {
    private final AnimalRepository animalRepository;
    private final SectionRepository sectionRepository;

    public void addSection(Section section){
        sectionRepository.save(section);
    }

    public void removeSection(Long id){
        sectionRepository.deleteById(id);
    }

    public void addAnimal(Animal animal){
        animalRepository.save(animal);
    }

    public void updateSection(Section section){
        sectionRepository.save(section);
    }

    public void removeAnimal(Long id){
        animalRepository.deleteById(id);
    }

    public void updateAnimal(Animal animal){
        animalRepository.save(animal);
    }

    public Animal getAnimal(Long id){
        Optional<Animal> animal = animalRepository.findById(id);

        if(animal.isEmpty())
            throw new IllegalStateException("Animal not exist");

        return animal.get();
    }

    public Section getSection(Long id){
        Optional<Section> section = sectionRepository.findById(id);

        if(section.isEmpty())
            throw new IllegalStateException("Section not exist");

        return section.get();
    }


    public List<Section> getAllSections(){
        return sectionRepository.findAll();
    }

    public List<Animal> getAllAnimals(){
        return animalRepository.findAll();
    }

    public List<Section> getSectionsOnMap(){
        return sectionRepository.getAllSectionsOnMap();
    }
}
