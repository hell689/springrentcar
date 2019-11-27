package net.springrentcar.service;

import net.springrentcar.domain.Mark;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface MarkService {
    List<Mark> findAll();

    Mark findById(Long id);

    Mark findByMark(String mark);

    Mark save(Mark mark);

    void delete(Long id) throws DataIntegrityViolationException;
}
