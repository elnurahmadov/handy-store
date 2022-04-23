package app.controller;

import app.externalapi.cityapi.CityService;
import app.form.FormUser;
import app.security.UserrDetails;
import app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@Controller
@AllArgsConstructor
@RequestMapping("/update")
public class UserUpdateController {

    private final UserService userService;
    private final CityService cityService;

    @GetMapping()
    public String handle_get(Model model, Authentication au) {
        model.addAttribute("loggedUser", userService.findById(String.valueOf(getLoggedUser(au).getId())));
        model.addAttribute("cities", cityService.getCities());
        return "update-profile";
    }

    @PostMapping()
    public RedirectView handle_post(FormUser form,
                                    @RequestParam("image") MultipartFile file,
                                    Model model,
                                    Authentication au) {
        userService.updateUser(String.valueOf(getLoggedUser(au).getId()), form.getName(), form.getSurname(), form.getCity(), form.getNumber(), file);

        model.addAttribute("process", "profileupdated");
        return new RedirectView("dashboard/1");
    }

    UserrDetails getLoggedUser(Authentication authentication) {
        return (UserrDetails) authentication.getPrincipal();
    }

}
