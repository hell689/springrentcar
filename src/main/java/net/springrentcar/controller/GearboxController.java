package net.springrentcar.controller;

import net.springrentcar.domain.Gearbox;
import net.springrentcar.service.GearboxService;
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
@RequestMapping("/gearbox")
public class GearboxController {

    private GearboxService gearboxService;

    public GearboxController(GearboxService gearboxService) {
        this.gearboxService = gearboxService;
    }

    @GetMapping
    public String gearboxes(Model model) {
        List<Gearbox> gearboxes = gearboxService.findAll();
        model.addAttribute("gearboxes", gearboxes);
        return "/gearbox";
    }

    @PostMapping("/save")
    public String addGearbox(@RequestParam(name = "gearbox", required = true) String gearboxValue,
                             Model model) {
        Gearbox gearbox = gearboxService.findByGearbox(gearboxValue);
        if (Objects.isNull(gearbox)) {
            gearbox = new Gearbox();
        } else {
            String errorMessage = "not unique value!";
            return "redirect:../gearbox?errorMessage=" + errorMessage;
        }
        gearbox.setGearbox(gearboxValue);
        gearboxService.save(gearbox);
        return "redirect:../gearbox";
    }

    @PostMapping("/delete")
    public String deleteGearbox(@RequestParam(name = "deleteId", required = true) Long id,
                                Model model) throws UnsupportedEncodingException {
        if (Objects.nonNull(id)) {
            try {
                gearboxService.delete(id);
            } catch (DataIntegrityViolationException e) {
                String errorMessage = URLEncoder.encode("Ошибка при удалении коробки. Возможно она еще используется в системе.", "UTF-8");
                return "redirect:../gearbox?errorMessage=" + errorMessage;
            }
        }
        return "redirect:../gearbox";
    }
}
