package net.springrentcar.service;

import net.springrentcar.domain.Rent;
import net.springrentcar.repo.RentRepo;
import net.springrentcar.repo.RentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RentServiceImpl implements RentService {
    private RentRepository rentRepo;

    public RentServiceImpl(RentRepository rentRepo) {
        this.rentRepo = rentRepo;
    }

    @Override
    public List<Rent> findAll() {
        Iterable<Rent> rents = rentRepo.findAll();
        return (List<Rent>) rents;
    }

    @Override
    public Rent findById(Long id) {
        return rentRepo.findById(id).get();
    }

    @Override
    public List<Rent> findUserRents(String username) {
        return rentRepo.findByRequestUsername(username);
    }

    @Override
    public void save(Rent rent) {
        rentRepo.save(rent);
    }

    @Override
    public void delete(Long id) throws DataIntegrityViolationException {
        rentRepo.deleteById(id);
    }

}
