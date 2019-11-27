package net.springrentcar.repo;

import net.springrentcar.domain.Color;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends CrudRepository<Color, Long> {
    Color findByColor(String color);

    void deleteById(Long id);
}
