package pl.javastart.mailapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.mailapp.user.User;
import pl.javastart.mailapp.user.UserPasswordDto;
import pl.javastart.mailapp.user.UserService;

import java.util.Optional;

@Controller
public class PasswordResetController {
    private final UserService userService;

    public PasswordResetController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/password-reset")
    String passwordResetForm() {
        return "password-reset-form";
    }

    @PostMapping("/password-reset")
    String passwordReset(@RequestParam("email") String email) {
        userService.resetPassword(email);
        return "password-reset-confirmation";
    }

    @GetMapping("/password-reset-form")
    String changePasswordForm(@RequestParam("token") String token,
                              Model model) {
        Optional<User> userByToken = userService.findUserByToken(token);
        if (userByToken.isPresent()) {
            UserPasswordDto userPasswordDto = new UserPasswordDto();
            userPasswordDto.setToken(token);
            model.addAttribute("userPassword", userPasswordDto);
            return "change-password-form";
        } else {
            return "password-reset-fail";
        }
    }

    @PostMapping("/change-password")
    String changePassword(UserPasswordDto userPasswordDto) {
        if (userPasswordDto.getPassword().equals(userPasswordDto.getPasswordConfirm())) {
            userService.changePassword(
                    userPasswordDto.getToken(),
                    userPasswordDto.getPassword());
            return "redirect:/login";
        } else {
            return "redirect:/password-reset-form?token=" + userPasswordDto.getToken();
        }
    }
}
