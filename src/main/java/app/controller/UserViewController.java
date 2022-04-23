package app.controller;

import app.entity.Userr;
import app.security.UserrDetails;
import app.service.BlockedService;
import app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserViewController {

  private final UserService userService;
  private final BlockedService blockedService;

  @GetMapping("/{id}")
  public String handle_get(@PathVariable String id, Model model, Authentication au) {
    long loggedUserId = getLoggedUser(au).getId();
    Userr user = userService.findById(String.valueOf(getLoggedUser(au).getId()));

    blockedService.checkBlock(id, loggedUserId);

    model.addAttribute("loggedUser", user);
    model.addAttribute("user", userService.viewUser(id, loggedUserId));
    return "user";
  }

  @GetMapping("/block/{id}")
  public RedirectView handle_block(@PathVariable String id, Authentication au, Model model) {
    blockedService.addBlocked(String.valueOf(getLoggedUser(au).getId()), id);
    
    model.addAttribute("process", "blocking");
    return new RedirectView("/dashboard/1");
  }

  UserrDetails getLoggedUser(Authentication authentication) {
    return (UserrDetails) authentication.getPrincipal();
  }

}
