package net.springrentcar.service;

import net.springrentcar.domain.Car;
import net.springrentcar.domain.Request;
import net.springrentcar.repo.CarRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepo;

    public CarServiceImpl(CarRepository carRepo) {
        this.carRepo = carRepo;
    }

    @Override
    public List<Car> findAll() {
        Iterable<Car> cars = carRepo.findAll();
        return (List<Car>) cars;
    }

    @Override
    public Car findById(Long id) {
        return carRepo.findById(id).get();
    }

    @Override
    public void save(Car car) {
        carRepo.save(car);
    }

    @Override
    public void delete(Long id) throws DataIntegrityViolationException {
        carRepo.deleteById(id);
    }

    @Override
    public List<Car> getFreeCars(Date date) {
        return carRepo.findFreeCar(date);
    }

    @Override
    public List<Car> getSuitableCars(Request request) {
        Map<String, List<Car>> suitableCars = new HashMap<>();
        if (request.getColor() != null) {
            List<Car> sameColorCars = carRepo.findByColor(request.getColor());
            if (sameColorCars.size() > 0)
                suitableCars.put("Color", sameColorCars);
        }
        if (request.getGearbox() != null) {
            List<Car> sameGearboxCars = carRepo.findByGearbox(request.getGearbox());
            if (sameGearboxCars.size() > 0)
                suitableCars.put("Gearbox", sameGearboxCars);
        }
        if (request.getMark() != null) {
            List<Car> sameMarkCars = carRepo.findByMark(request.getMark());
            if (sameMarkCars.size() > 0)
                suitableCars.put("Mark", sameMarkCars);
        }
        if (request.getVolume() != 0f) {
            List<Car> sameVolumeCars = carRepo.findByVolume(request.getVolume());
            if (sameVolumeCars.size() > 0)
                suitableCars.put("Volume", sameVolumeCars);
        }

        Map<Car, Integer> mostSuitableCars = new HashMap<>();
        for (Map.Entry<String, List<Car>> entry : suitableCars.entrySet()) {
            for (Car car : entry.getValue()) {
                Integer count = mostSuitableCars.get(car);
                if (count == null) {
                    count = 0;
                }
                mostSuitableCars.put(car, ++count);
            }
        }
        List<Car> sameCars = new ArrayList<>();
        for (Map.Entry<Car, Integer> entry : mostSuitableCars.entrySet()) {
            if (entry.getValue() >= 2)
                sameCars.add(entry.getKey());
        }
        suitableCars.put("Most suitable cars", sameCars); //не используется, но пусть будет))
        return sameCars;
    }
}
