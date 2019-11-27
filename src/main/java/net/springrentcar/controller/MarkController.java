package net.springrentcar.controller;

import net.springrentcar.domain.Mark;
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
@RequestMapping("/mark")
public class MarkController {

    private MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @GetMapping
    public String marks(Model model) {
        List<Mark> marks = markService.findAll();
        model.addAttribute("marks", marks);
        return "/mark";
    }

    @PostMapping("/save")
    public String addMark(@RequestParam(name = "mark", required = true) String markValue,
                          Model model) {
        Mark mark = markService.findByMark(markValue);
        if (Objects.isNull(mark)) {
            mark = new Mark();
        } else {
            String errorMessage = "not unique value!";
            return "redirect:../mark?errorMessage=" + errorMessage;
        }
        mark.setMark(markValue);
        markService.save(mark);
        return "redirect:../mark";
    }

    @PostMapping("/delete")
    public String deleteMark(@RequestParam(name = "deleteId", required = true) Long id, Model model) throws UnsupportedEncodingException {
        if (Objects.nonNull(id)) {
            try {
                markService.delete(id);
            } catch (DataIntegrityViolationException e) {
                String errorMessage = URLEncoder.encode("Ошибка при удалении марки. Возможно она еще используется в системе.", "UTF-8");
                return "redirect:../mark?errorMessage=" + errorMessage;
            }
        }
        return "redirect:../mark";
    }
}
