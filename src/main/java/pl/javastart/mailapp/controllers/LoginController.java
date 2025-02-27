package pl.javastart.mailapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    String loginForm(@RequestParam(value = "error", required = false) String error,
                     @RequestParam(value = "logout", required = false) String logout,
                     Model model) {
        if (error != null && error.isEmpty()) {
            model.addAttribute("error", true);
        }
        if (logout != null && logout.isEmpty()) {
            model.addAttribute("logout", true);
        }
        return "login";
    }
}
