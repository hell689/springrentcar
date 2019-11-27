package net.springrentcar.service;

import net.springrentcar.domain.Mark;
import net.springrentcar.repo.MarkRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkServiceImpl implements MarkService {

    private MarkRepository markRepository;

    MarkServiceImpl(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @Override
    public List<Mark> findAll() {
        Iterable<Mark> marks = markRepository.findAll();
        return (List<Mark>) marks;
    }

    @Override
    public Mark findById(Long id) {
        return markRepository.findById(id).get();
    }

    @Override
    public Mark findByMark(String mark) {
        return markRepository.findByMark(mark);
    }

    @Override
    public Mark save(Mark mark) {
        return markRepository.save(mark);
    }

    @Override
    public void delete(Long id) throws DataIntegrityViolationException {
        markRepository.deleteById(id);
    }

}
