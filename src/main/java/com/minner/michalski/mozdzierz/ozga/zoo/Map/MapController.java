package com.minner.michalski.mozdzierz.ozga.zoo.Map;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
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

    @PutMapping("/{pathId}")
    public void setVisitedPathNode(@PathVariable("pathId") Long pathElementId){
        service.setVisited(pathElementId);
    }

    @PutMapping
    public void setVisitedPathNode(@RequestBody @NotNull PathElement element){
        service.setVisited(element.getId(), element.getIsVisited());
    }
}
