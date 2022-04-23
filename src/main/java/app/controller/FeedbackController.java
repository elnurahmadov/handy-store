package app.controller;


import app.security.UserrDetails;
import app.service.MailRequestService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {
  private final MailRequestService mailRequestService;

  @GetMapping
  public String handle_get() {
    return "feedback";
  }

  @PostMapping
  public RedirectView handle_post(@RequestParam String feedback, Model model, Authentication au) {
    mailRequestService.sendFeedback(getLoggedUser(au).getUsername(), feedback);
    model.addAttribute("process", "feedback");
    return new RedirectView("/dashboard/1");
  }

  UserrDetails getLoggedUser(Authentication authentication) {
    return (UserrDetails) authentication.getPrincipal();
  }

}
