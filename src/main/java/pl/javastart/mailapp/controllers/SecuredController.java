package pl.javastart.mailapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecuredController {

    @GetMapping("/secured")
    String secured() {
        return "secured";
    }
}
