package app.controller;

import app.form.FormAd;
import app.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class IndexController {

  private final MailRequestService mailRequestService;
  private final AboutUsService aboutUsService;
  private final MissionService missionService;
  private final PhoneService phoneService;
  private final AddressService addressService;

  // http://localhost:8080/
  @GetMapping
  public String handle_get(Model model) {
    model.addAttribute("aboutUs", aboutUsService.findAll());
    model.addAttribute("missions", missionService.findAll());
    model.addAttribute("phones", phoneService.findAll());
    model.addAttribute("addresses", addressService.findAll());
    return "index";
  }

  @PostMapping
  public RedirectView handle_post(FormAd form, Model model) {
    mailRequestService.sendAdRequest(form.getFullName(), form.getNumber(), form.getTime());
    model.addAttribute("process", "adv");
    return new RedirectView("/");
  }
}
