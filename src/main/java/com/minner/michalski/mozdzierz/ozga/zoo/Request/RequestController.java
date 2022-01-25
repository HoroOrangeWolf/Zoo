package com.minner.michalski.mozdzierz.ozga.zoo.Request;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path="api/v1/request")
@AllArgsConstructor
public class RequestController {

    private final RequestService service;

    @PostMapping
    public void addRequest(@Valid @RequestBody Request request){
        service.addRequest(request);
    }

    @DeleteMapping("/{idRequest}")
    public void removeRequest(@PathVariable("idRequest") Long id){
        service.removeRequest(id);
    }

    @PutMapping
    public void updateRequest(@RequestBody Request request){
        service.updateRequest(request);
    }

    @GetMapping("/{idRequest}")
    public Request getRequestsById(@PathVariable("idRequest") Long id){
        return service.getRequestById(id);
    }

    @GetMapping
    public Request getNextRequest(){
        return service.getNextRequest();
    }

}
