package net.springrentcar.repo;

import net.springrentcar.domain.Rent;

import java.util.List;

public interface RentRepo {

    List<Rent> findAll();

    Rent findById(Long id);

    void delete(Long id);

    void insert(Rent rent);

    void update(Rent rent);

    List<Rent> findByUser(String username);
}
