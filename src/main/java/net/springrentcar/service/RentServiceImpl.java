package net.springrentcar.service;

import net.springrentcar.domain.Rent;
import net.springrentcar.repo.RentRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RentServiceImpl implements RentService {
    private RentRepo rentRepo;

    public RentServiceImpl(RentRepo rentRepo) {
        this.rentRepo = rentRepo;
    }

    @Override
    public List<Rent> findAll() {
        return rentRepo.findAll();
    }

    @Override
    public Rent findById(Long id) {
        return rentRepo.findById(id);
    }

    @Override
    public List<Rent> findUserRents(String username) {
        return rentRepo.findByUser(username);
    }

    @Override
    public void save(Rent rent) {
        if (Objects.nonNull(rent.getId())) {
            rentRepo.update(rent);
        } else {
            rentRepo.insert(rent);
        }
    }

    @Override
    public void delete(Long id) throws DataIntegrityViolationException {
        rentRepo.delete(id);
    }

}
