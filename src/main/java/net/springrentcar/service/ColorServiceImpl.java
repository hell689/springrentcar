package net.springrentcar.service;

import net.springrentcar.domain.Color;
import net.springrentcar.repo.ColorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {

    private ColorRepository colorRepository;

    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public List<Color> findAll() {
        Iterable<Color> colors = colorRepository.findAll();
        return (List<Color>) colors;
    }

    @Override
    public Color findById(Long id) {
        return colorRepository.findById(id).get();
    }

    @Override
    public Color findByColor(String color) {
        return colorRepository.findByColor(color);
    }

    @Override
    public Color save(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public void delete(Long id) throws DataIntegrityViolationException {
        colorRepository.deleteById(id);
    }

}
