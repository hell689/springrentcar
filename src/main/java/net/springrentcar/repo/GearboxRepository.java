package net.springrentcar.repo;

import net.springrentcar.domain.Gearbox;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearboxRepository extends CrudRepository<Gearbox, Long> {
    Gearbox findByGearbox(String gearbox);

    void deleteById(Long id);
}
