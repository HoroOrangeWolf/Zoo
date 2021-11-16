package com.minner.michalski.mozdzierz.ozga.zoo.Request;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/request")
public class RequestController {

    private final RequestService service;

    @PostMapping
    public void addRequest(@RequestBody Request request){
        service.addRequest(request);
    }

    @DeleteMapping("/{idRequest}")
    public void removeRequest(@PathVariable("idRequest") Long id){
        service.removeRequest(id);
    }

    @GetMapping
    public List<Request> getRequestsByStatus(@RequestParam("status") Status status) {
        return service.getRequestsByStatus(status);
    }
}
