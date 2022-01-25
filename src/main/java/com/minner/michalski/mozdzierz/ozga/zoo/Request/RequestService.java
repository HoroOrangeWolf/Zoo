package com.minner.michalski.mozdzierz.ozga.zoo.Request;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    public void addRequest(Request request){
        requestRepository.save(request);
    }
    //nie trzeba robić wyjątku że nie ma id nie jest to tak ważne jak w user
    public void removeRequest(Long id){
        requestRepository.deleteById(id);
    }

    public void updateRequest(Request request){
        requestRepository.save(request);
    }

    public Request getNextRequest(){
        List<Request> nextRequests = requestRepository.getNextRequests();

        Optional<Request> first = nextRequests.stream().findFirst();

        if(first.isEmpty())
            throw new IllegalStateException("There is no more requests!");

        Request request = first.get();

        request.setStatus(Status.ROZPATRYWANY);

        requestRepository.save(request);

        return request;
    }

    public Request getRequestById(Long id){
        Optional<Request> byId = requestRepository.findById(id);

        if(byId.isEmpty())
            throw new IllegalStateException("Invalid id");


        return byId.get();
    }

}
