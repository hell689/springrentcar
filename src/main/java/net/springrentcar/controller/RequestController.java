package net.springrentcar.controller;

import net.springrentcar.domain.*;
import net.springrentcar.service.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/request")
public class RequestController {

    private RequestService requestService;
    private CarService carService;
    private ColorService colorService;
    private MarkService markService;
    private GearboxService gearboxService;

    public RequestController(RequestService requestService, CarService carService, ColorService colorService,
                             MarkService markService, GearboxService gearboxService) {
        this.requestService = requestService;
        this.carService = carService;
        this.colorService = colorService;
        this.markService = markService;
        this.gearboxService = gearboxService;
    }

    @GetMapping
    public String requests(Model model) {
        List<Request> requests;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            requests = requestService.findAll();
        } else {
            requests = requestService.getUserRequests(auth.getName());
        }

        List<Mark> marks = markService.findAll();
        List<Color> colors = colorService.findAll();
        List<Gearbox> gearboxes = gearboxService.findAll();
        model.addAttribute("requests", requests);
        model.addAttribute("marks", marks);
        model.addAttribute("colors", colors);
        model.addAttribute("gearboxes", gearboxes);
        return "/request";
    }

    @PostMapping("/process")
    public String processRequest(@RequestParam(name = "requestId") Long requestId, Model model) {
        Request request = requestService.findById(requestId);
        if (request.isProcessed()) {
            return "redirect:../request";
        } else {
            List<Car> freeCars = carService.getFreeCars(request.getEndDate());
            List<Car> suitableCars = carService.getSuitableCars(request);
            if (Objects.nonNull(suitableCars)) {
                if (!suitableCars.isEmpty()) model.addAttribute("suitableCars", suitableCars);
            }
            model.addAttribute("freeCars", freeCars);
            model.addAttribute("processedRequest", request);
            return "/addrent";
        }
    }

    @PostMapping("/save")
    public String addRequest(@RequestParam(name = "color") Long colorId,
                             @RequestParam(name = "mark") Long markId,
                             @RequestParam(name = "gearbox") Long gearboxId,
                             @RequestParam(name = "volume") Float volume,
                             @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                             @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                             @RequestParam(name = "comment") String comment,
                             Model model) throws UnsupportedEncodingException {
        if (startDate.after(new Date()) && startDate.before(endDate)) {
            Request request = new Request();
            request.setColor(colorService.findById(colorId));
            request.setMark(markService.findById(markId));
            request.setGearbox(gearboxService.findById(gearboxId));
            request.setVolume(volume);
            request.setStartDate(startDate);
            request.setEndDate(endDate);
            request.setComment(comment);
            request.setProcessed(false);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            request.setUsername(auth.getName());
            requestService.save(request);
            return "redirect:../request";
        } else {
            String errorMessage = URLEncoder.encode("Ошибка сохранения заявки! Проверте правильность заполнения!", "UTF8");
            return"redirect:../request?errorMessage=" + errorMessage;
        }
    }

    @PostMapping("/delete")
    public String deleteRequest(@RequestParam(name = "deleteId") Long id, Model model) throws UnsupportedEncodingException {
        if (Objects.nonNull(id)) {
            try {
                requestService.delete(id);
            } catch (DataIntegrityViolationException e) {
                String errorMessage = URLEncoder.encode("Ошибка при удалении запроса. Возможно он еще используется в системе.", "UTF-8");
                return "redirect:../request?errorMessage=" + errorMessage;
            }
        }
        return "redirect:../request";
    }
}
