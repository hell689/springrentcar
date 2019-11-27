package net.springrentcar.controller;

import net.springrentcar.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String formPage() {
        return "/registration";
    }

    @PostMapping
    public String registration(@RequestParam(name = "username") String username,
                               @RequestParam(name = "password") String password, Model model) throws UnsupportedEncodingException {
        try {
            userService.createUser(username, password, "ROLE_USER");
        } catch (DuplicateKeyException e) {
            model.addAttribute("errorMessage", "Пользователь " + username + " уже зарегистрирован в системе.");
            return "/registration";
        }
        String message = URLEncoder.encode("Регистрация прошла успешно!", "UTF-8");
        return "redirect:/?message=" + message;
    }
}
