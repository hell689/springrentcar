package net.springrentcar.service;

import net.springrentcar.domain.Color;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface ColorService {
    List<Color> findAll();

    Color findById(Long id);

    Color findByColor(String color);

    Color save(Color color);

    void delete(Long id) throws DataIntegrityViolationException;
}
