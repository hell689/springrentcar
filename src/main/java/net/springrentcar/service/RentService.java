package net.springrentcar.service;

import net.springrentcar.domain.Rent;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface RentService {
    List<Rent> findAll();

    Rent findById(Long id);

    List<Rent> findUserRents(String username);

    void save(Rent rent);

    void delete(Long id) throws DataIntegrityViolationException;
}
