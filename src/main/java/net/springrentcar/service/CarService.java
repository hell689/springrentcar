package net.springrentcar.service;

import net.springrentcar.domain.Car;
import net.springrentcar.domain.Request;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Date;
import java.util.List;

public interface CarService {
    List<Car> findAll();

    Car findById(Long id);

    void save(Car car);

    void delete(Long id) throws DataIntegrityViolationException;

    List<Car> getFreeCars(Date startDate);

    List<Car> getSuitableCars(Request request);

}
