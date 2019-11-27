package net.springrentcar.repo;

import net.springrentcar.domain.Car;
import net.springrentcar.domain.Request;

import java.util.Date;
import java.util.List;

public interface CarRepo {

    List<Car> findAll();

    Car findById(Long id);

    void delete(Long id);

    void insert(Car car);

    void update(Car car);

    List<Car> findFreeCar(Date date);

    List<Car> findSuitableCars(Request request);
}
