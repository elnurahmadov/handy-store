package app.controller;

import app.form.FormSearch;
import app.security.UserrDetails;
import app.service.CategoryService;
import app.service.UserService;
import app.service.WishedService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@Controller
@AllArgsConstructor
@RequestMapping("/wishlist")
public class WishedController {

  private final WishedService wishedService;
  private final CategoryService categoryService;
  private final UserService userService;

  @GetMapping
  public String handle_get(Model model, Authentication au) {
    model
            .addAttribute("posts", wishedService.findWishedPosts(String.valueOf(getLoggedUser(au).getId())))
            .addAttribute("categories", categoryService.findAll())
            .addAttribute("wished", true)
            .addAttribute("loggedUser", userService.findByEmail(getLoggedUser(au).getUsername()));
    return "dashboard";
  }

  @RequestMapping("/add/{id}")
  public String handle_add(@PathVariable String id, Authentication au) {
    wishedService.addWished(String.valueOf(getLoggedUser(au).getId()), id);
    return "redirect:/wishlist";
  }

  @RequestMapping("/delete/{id}")
  public String handle_delete(@PathVariable String id, Authentication au) {
    wishedService.deleteWished(String.valueOf(getLoggedUser(au).getId()), id);
    return "redirect:/wishlist";
  }

  @PostMapping()
  public RedirectView handle_post(Model model, FormSearch form) {
    model.addAttribute("name", form.getKeyword());
    model.addAttribute("cat", form.getCategory());
    return new RedirectView("search");
  }

  UserrDetails getLoggedUser(Authentication authentication) {
    return (UserrDetails) authentication.getPrincipal();
  }

}
