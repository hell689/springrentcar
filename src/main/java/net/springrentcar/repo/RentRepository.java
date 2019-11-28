package net.springrentcar.repo;

import net.springrentcar.domain.Rent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends CrudRepository<Rent, Long> {

    void deleteById(Long id);

    List<Rent> findByRequestUsername(String username);
}
