package net.springrentcar.controller;

import net.springrentcar.domain.Car;
import net.springrentcar.domain.Color;
import net.springrentcar.domain.Gearbox;
import net.springrentcar.domain.Mark;
import net.springrentcar.service.CarService;
import net.springrentcar.service.ColorService;
import net.springrentcar.service.GearboxService;
import net.springrentcar.service.MarkService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/car")
public class CarController {

    private CarService carService;
    private ColorService colorService;
    private MarkService markService;
    private GearboxService gearboxService;


    public CarController(CarService carService, ColorService colorService,
                         MarkService markService, GearboxService gearboxService) {
        this.carService = carService;
        this.colorService = colorService;
        this.markService = markService;
        this.gearboxService = gearboxService;
    }

    @GetMapping
    public String cars(Model model) {
        List<Car> cars = carService.findAll();
        List<Mark> marks = markService.findAll();
        List<Color> colors = colorService.findAll();
        List<Gearbox> gearboxes = gearboxService.findAll();
        model.addAttribute("cars", cars);
        model.addAttribute("marks", marks);
        model.addAttribute("colors", colors);
        model.addAttribute("gearboxes", gearboxes);
        return "/car";
    }

    @PostMapping("/save")
    public String addCar(@RequestParam(name = "color") Long colorId,
                         @RequestParam(name = "mark") Long markId,
                         @RequestParam(name = "gearbox") Long gearboxId,
                         @RequestParam(name = "volume") Float volume,
                         Model model) {
        Car car = new Car();
        car.setColor(colorService.findById(colorId));
        car.setMark(markService.findById(markId));
        car.setGearbox(gearboxService.findById(gearboxId));
        car.setVolume(volume);
        carService.save(car);
        return "redirect:../car";
    }

    @PostMapping("/delete")
    public String deleteCar(@RequestParam(name = "deleteId", required = true) Long id, Model model) throws UnsupportedEncodingException {
        if (Objects.nonNull(id)) {
            try {
                carService.delete(id);
            } catch (DataIntegrityViolationException e) {
                String errorMessage = URLEncoder.encode("Ошибка при удалении автомобиля. Возможно он еще используется в системе.", "UTF-8");
                return "redirect:../car?errorMessage=" + errorMessage;
            }
        }
        return "redirect:../car";
    }
}
