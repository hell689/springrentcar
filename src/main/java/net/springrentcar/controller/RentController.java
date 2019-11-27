package net.springrentcar.controller;

import net.springrentcar.domain.Car;
import net.springrentcar.domain.Rent;
import net.springrentcar.domain.Request;
import net.springrentcar.service.CarService;
import net.springrentcar.service.RentService;
import net.springrentcar.service.RequestService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/rent")
public class RentController {

    private RentService rentService;
    private RequestService requestService;
    private CarService carService;

    public RentController(RentService rentService, RequestService requestService, CarService carService) {
        this.rentService = rentService;
        this.requestService = requestService;
        this.carService = carService;
    }

    @GetMapping
    public String rents(Model model) {
        List<Rent> rents;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            rents = rentService.findAll();
        } else {
            rents = rentService.findUserRents(auth.getName());
        }
        model.addAttribute("rents", rents);
        return "/rent";
    }

    @PostMapping("/add")
    public String addRequest(@RequestParam(name = "requestId") Long requestId,
                             @RequestParam(name = "carId") Long carId, Model model) {
        Request request = requestService.findById(requestId);
        if (request.isProcessed()) {
            return "redirect:../request";
        } else {
            Car car = carService.findById(carId);
            Rent rent = new Rent();
            rent.setRequest(request);
            rent.setCar(car);
            rent.setStartDate(request.getStartDate());
            rent.setEndDate(request.getEndDate());
            rentService.save(rent);
            request.setProcessed(true);
            requestService.save(request);
            return "redirect:../rent";
        }
    }

    @PostMapping("/delete")
    public String deleteRequest(@RequestParam(name = "deleteId") Long id, Model model) throws UnsupportedEncodingException {
        if (Objects.nonNull(id)) {
            try {
                rentService.delete(id);
            } catch (DataIntegrityViolationException e) {
                String errorMessage = URLEncoder.encode("Ошибка при удалении аренды.", "UTF-8");
                return "redirect:../rents?errorMessage=" + errorMessage;
            }
        }
        return "redirect:../rent";
    }
}
