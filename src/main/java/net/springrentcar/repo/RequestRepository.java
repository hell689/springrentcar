package net.springrentcar.repo;

import net.springrentcar.domain.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {

    void deleteById(Long id);

    List<Request> findByUsername(String username);
}
