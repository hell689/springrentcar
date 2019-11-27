package net.springrentcar.service;

import net.springrentcar.domain.Gearbox;
import net.springrentcar.repo.GearboxRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GearboxServiceImpl implements GearboxService {

    private GearboxRepository gearboxRepository;

    GearboxServiceImpl(GearboxRepository gearboxRepository) {
        this.gearboxRepository = gearboxRepository;
    }

    @Override
    public List<Gearbox> findAll() {
        Iterable<Gearbox> gearboxes = gearboxRepository.findAll();
        return (List<Gearbox>) gearboxes;
    }

    @Override
    public Gearbox findById(Long id) {
        return gearboxRepository.findById(id).get();
    }

    @Override
    public Gearbox findByGearbox(String gearbox) {
        return gearboxRepository.findByGearbox(gearbox);
    }

    @Override
    public Gearbox save(Gearbox gearbox) {
        return gearboxRepository.save(gearbox);
    }

    @Override
    public void delete(Long id) throws DataIntegrityViolationException {
        gearboxRepository.deleteById(id);
    }

}
