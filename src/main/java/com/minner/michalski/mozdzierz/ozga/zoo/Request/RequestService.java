package com.minner.michalski.mozdzierz.ozga.zoo.Request;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    public void addRequest(Request request){
        requestRepository.save(request);
    }

    public void removeRequest(Long id){
        requestRepository.deleteById(id);
    }

    public void updateRequest(Request request){
        requestRepository.save(request);
    }

    public List<Request> getRequestsByStatus(Status status){
        return requestRepository.getRequestByStatus(status);
    }





}
