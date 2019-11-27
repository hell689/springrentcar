package net.springrentcar.repo;

import net.springrentcar.domain.Mark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends CrudRepository<Mark, Long> {
    Mark findByMark(String mark);

    void deleteById(Long id);
}
