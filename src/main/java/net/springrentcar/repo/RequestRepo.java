package net.springrentcar.repo;

import net.springrentcar.domain.Request;

import java.util.List;

public interface RequestRepo {

    List<Request> findAll();

    Request findById(Long id);

    void delete(Long id);

    void insert(Request request);

    void update(Request request);

    List<Request> findUserRequests(String username);
}
