package net.springrentcar.service;

import org.springframework.dao.DuplicateKeyException;

public interface UserService {
    void createUser(String username, String password, String role) throws DuplicateKeyException;
}
