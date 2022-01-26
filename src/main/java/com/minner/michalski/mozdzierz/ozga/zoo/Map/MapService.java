package com.minner.michalski.mozdzierz.ozga.zoo.Map;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MapService {
    private PathRepository pathRepository;
    private PathElementRepository pathElementRepository;

    public void addPath(Path path){
        pathRepository.save(path);
    }

    public void removePath(Long id){
        pathRepository.deleteById(id);
    }

    public void updatePath(Path path){
        pathRepository.save(path);
    }

    public void setVisited(Long pathElementId, Boolean isVisited){
        Optional<PathElement> ticketById = pathElementRepository.findById(pathElementId);

        if(ticketById.isEmpty())
            throw new IllegalStateException("Path element by id: " + pathElementId + " is not existing!");

        PathElement pathElement = ticketById.get();

        pathElement.setIsVisited(isVisited);

        pathElementRepository.save(pathElement);
    }

    public Path getPath(Long pathId){
        Optional<Path> pathById = pathRepository.findById(pathId);

        if(pathById.isEmpty())
            throw new IllegalStateException("Path by id: " + pathId + " is not existing!");

        return pathById.get();
    }


}
