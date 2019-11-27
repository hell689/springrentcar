package net.springrentcar.service;

import net.springrentcar.domain.Gearbox;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface GearboxService {
    List<Gearbox> findAll();

    Gearbox findById(Long id);

    Gearbox findByGearbox(String gearbox);

    Gearbox save(Gearbox gearbox);

    void delete(Long id) throws DataIntegrityViolationException;
}
