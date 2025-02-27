package pl.javastart.mailapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.mailapp.user.UserRegistrationDto;
import pl.javastart.mailapp.user.UserService;

@Controller
class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    String registrationForm(Model model) {
        UserRegistrationDto user = new UserRegistrationDto();
        model.addAttribute("user", user);
        return "registration-form";
    }

    @PostMapping("/register")
    String register(UserRegistrationDto userRegistrationDto) {
        userService.register(userRegistrationDto);
        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    String registrationConfirmation() {
        return "registration-confirmation";
    }

    @GetMapping("/confirm")
    String confirmRegistration(@RequestParam("token") String token) {
        if (userService.confirmRegistration(token)) {
            return "registration-confirmation-success";
        } else {
            return "registration-confirmation-fail";
        }
    }
}