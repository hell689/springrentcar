package net.springrentcar.service;

import net.springrentcar.domain.Request;
import net.springrentcar.repo.RequestRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RequestServiceImpl implements RequestService {
    private RequestRepo requestRepo;

    public RequestServiceImpl(RequestRepo requestRepo) {
        this.requestRepo = requestRepo;
    }

    @Override
    public List<Request> findAll() {
        return requestRepo.findAll();
    }

    @Override
    public Request findById(Long id) {
        return requestRepo.findById(id);
    }

    @Override
    public void save(Request request) {
        if (Objects.nonNull(request.getId())) {
            requestRepo.update(request);
        } else {
            requestRepo.insert(request);
        }
    }

    @Override
    public void delete(Long id) throws DataIntegrityViolationException {
        requestRepo.delete(id);
    }

    @Override
    public List<Request> getUserRequests(String username) {
        return requestRepo.findUserRequests(username);
    }
}
