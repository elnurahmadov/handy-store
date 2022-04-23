package app.controller;

import app.form.FormNewPass;
import app.form.FormReset;
import app.service.MailSenderService;
import app.service.ResetPasswordService;
import app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import java.util.Random;

@Controller
@AllArgsConstructor
@RequestMapping("resetpass")
public class ResetPasswordController {
    private final MailSenderService sender;
    private final UserService userService;
    private final ResetPasswordService resetPasswordService;


    /**
     * http://localhost:8080/resetpass/
     */
    @GetMapping
    public String handle_get() {
        return "forgot-pass";
    }

    /**
     * http://localhost:8080/resetpass/
     */
    @PostMapping
    public RedirectView handle_post(FormReset form, Model model) throws MessagingException {
        String message = "Someone sent reset password request for your HANDY profile. If it was you, please click link below to reset your password";
        Random r = new Random();
        String token = String.valueOf(r.nextInt(Integer.MAX_VALUE));

        String link = String.format("https://http://localhost:5000/resetpass/newpass?email=%s&token=%s",
                form.getEmail(), token);
        String text = String.format("%s \n %s", message, link);

        if (!userService.isUserExistByEmail(form.getEmail())) model.addAttribute("sent", false);
        else {
            resetPasswordService.addOrUpdateResetToken(form.getEmail(), token);
            sender.send(form.getEmail(), "Reset your password for HANDY profile", text);
            model.addAttribute("sent", true);
        }

        return new RedirectView("/resetpass");
    }

    @GetMapping("/newpass")
    public String handle_newpass_get(@RequestParam() String email, @RequestParam String token) {
        resetPasswordService.validateToken(email, token);
        return "new-pass";
    }

    @PostMapping("/newpass")
    public String handle_newpass_post(@RequestParam() String email,
                                      @RequestParam() String token,
                                      FormNewPass form, Model model) {
        resetPasswordService.validateToken(email, token);
        userService.updatePassword(email, form.getPass(), form.getConPass());
        resetPasswordService.deleteToken(email);
        model.addAttribute("process", "reset");
        return "redirect:/signin";
    }

}
