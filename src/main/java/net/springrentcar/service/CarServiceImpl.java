package net.springrentcar.service;

import net.springrentcar.domain.Car;
import net.springrentcar.domain.Request;
import net.springrentcar.repo.CarRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CarServiceImpl implements CarService {

    private CarRepo carRepo;

    public CarServiceImpl(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = carRepo.findAll();
        return cars;
    }

    @Override
    public Car findById(Long id) {
        return carRepo.findById(id);
    }

    @Override
    public void save(Car car) {
        if (Objects.nonNull(car.getId())) {
            carRepo.update(car);
        } else {
            carRepo.insert(car);
        }
    }

    @Override
    public void delete(Long id) throws DataIntegrityViolationException {
        carRepo.delete(id);
    }

    @Override
    public List<Car> getFreeCars(Date date) {
        return carRepo.findFreeCar(date);
    }

    @Override
    public List<Car> getSuitableCars(Request request) {
        return carRepo.findSuitableCars(request);
    }

}
