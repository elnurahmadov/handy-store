package app.controller;

import app.form.FormReg;
import app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@AllArgsConstructor
@Controller
@RequestMapping("/signup")
public class RegistrationController {

  private final UserService userService;

  @GetMapping
  public String handle_get(Model model) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (!(auth instanceof AnonymousAuthenticationToken)) {
      return "redirect:/dashboard";
    }
    return "signup";
  }

  @PostMapping
  public RedirectView handle_post(FormReg form) {
    userService.register(form.getEmail(), form.getPass(), form.getConPass());
    return new RedirectView("/signin");
  }
}
