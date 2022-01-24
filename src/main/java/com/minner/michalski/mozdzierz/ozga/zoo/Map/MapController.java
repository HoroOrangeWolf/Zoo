package com.minner.michalski.mozdzierz.ozga.zoo.Map;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/map")
@AllArgsConstructor
public class MapController {

    private MapService service;

    @GetMapping("/{pathId}")
    public Path getPath(@PathVariable("pathId") Long pathId){
        return service.getPath(pathId);
    }

    @PutMapping("/pathelement/{pathId}")
    public void setVisitedPathNode(@PathVariable("pathId") Long pathElementId, @Param("visited") Boolean visited){
        service.setVisited(pathElementId, visited);
    }

}
