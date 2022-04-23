package app.controller;

import app.externalapi.cityapi.CityService;
import app.form.FormInfo;
import app.security.UserrDetails;
import app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@AllArgsConstructor
@Controller
@RequestMapping("/info")
public class UserInfoController {

  private final UserService userService;
  private final CityService cityService;

  @GetMapping
  public String handle_get(Model model, Authentication au) {
    if (userService.isInfoFilled(getLoggedUser(au).getId()))
      return "redirect:/dashboard/1";

    model.addAttribute("cities", cityService.getCities());
    model.addAttribute("loggedUser", userService.findByEmail(getLoggedUser(au).getUsername()));
    return "anket";
  }

  @PostMapping
  public RedirectView handle_post(FormInfo form,
                                  @RequestParam("image") MultipartFile file,
                                  Authentication au) {
    userService.fillInfo(
            String.valueOf(getLoggedUser(au).getId()), form.getName(),
            form.getSurname(), form.getCity(), form.getNumber(), file);
    return new RedirectView("dashboard");
  }

  UserrDetails getLoggedUser(Authentication authentication) {
    return (UserrDetails) authentication.getPrincipal();
  }

}
