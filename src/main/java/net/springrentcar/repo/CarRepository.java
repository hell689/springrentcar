package net.springrentcar.repo;

import net.springrentcar.domain.Car;
import net.springrentcar.domain.Color;
import net.springrentcar.domain.Gearbox;
import net.springrentcar.domain.Mark;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

    void deleteById(Long id);

    List<Car> findByColor(Color color);

    List<Car> findByGearbox(Gearbox gearbox);

    List<Car> findByMark(Mark mark);

    @Query("select c from Car c where c.id not in (select r.car FROM Rent r where r.endDate > :date )")
    List<Car> findFreeCar(@Param("date") Date date);

    List<Car> findByVolume(float volume);
}
