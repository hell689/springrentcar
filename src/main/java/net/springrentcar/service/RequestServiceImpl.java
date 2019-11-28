package net.springrentcar.service;

import net.springrentcar.domain.Request;
import net.springrentcar.repo.RequestRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepo;

    public RequestServiceImpl(RequestRepository requestRepo) {
        this.requestRepo = requestRepo;
    }

    @Override
    public List<Request> findAll() {
        Iterable<Request> requests = requestRepo.findAll();
        return (List<Request>) requests;
    }

    @Override
    public Request findById(Long id) {
        return requestRepo.findById(id).get();
    }

    @Override
    public void save(Request request) {
        requestRepo.save(request);
    }

    @Override
    public void delete(Long id) throws DataIntegrityViolationException {
        requestRepo.deleteById(id);
    }

    @Override
    public List<Request> getUserRequests(String username) {
        return requestRepo.findByUsername(username);
    }
}
