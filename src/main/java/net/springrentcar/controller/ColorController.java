package net.springrentcar.controller;

import net.springrentcar.domain.Color;
import net.springrentcar.service.ColorService;
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
@RequestMapping("/color")
public class ColorController {

    private ColorService colorService;

    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping
    public String colors(Model model) {
        List<Color> colors = colorService.findAll();
        model.addAttribute("colors", colors);
        return "/color";
    }

    @PostMapping("/save")
    public String addColor(@RequestParam(name = "color", required = true) String colorValue,
                           Model model) {
        Color color = colorService.findByColor(colorValue);
        if (Objects.isNull(color)) {
            color = new Color();
        } else {
            String errorMessage = "not unique value!";
            return "redirect:../color?errorMessage=" + errorMessage;
        }
        color.setColor(colorValue);
        colorService.save(color);
        return "redirect:../color";
    }

    @PostMapping("/delete")
    public String deleteColor(@RequestParam(name = "deleteId", required = true) Long id, Model model) throws UnsupportedEncodingException {
        if (Objects.nonNull(id)) {
            try {
                colorService.delete(id);
            } catch (DataIntegrityViolationException e) {
                String errorMessage = URLEncoder.encode("Ошибка при удалении цвета. Возможно он еще используется в системе.", "UTF-8");
                return "redirect:../color?errorMessage=" + errorMessage;
            }
        }
        return "redirect:../color";
    }
}
