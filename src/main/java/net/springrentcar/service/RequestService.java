package net.springrentcar.service;

import net.springrentcar.domain.Request;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface RequestService {
    List<Request> findAll();

    Request findById(Long id);

    void save(Request request);

    void delete(Long id) throws DataIntegrityViolationException;

    List<Request> getUserRequests(String username);
}
