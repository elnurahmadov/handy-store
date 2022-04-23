package app.controller;

import app.externalapi.cityapi.CityService;
import app.form.FormPost;
import app.security.UserrDetails;
import app.service.CategoryService;
import app.service.PostService;
import app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@AllArgsConstructor
@Controller
@RequestMapping("/mypost")
public class PostOperationsController {

  private final PostService postService;
  private final CategoryService categoryService;
  private final CityService cityService;
  private final UserService userService;

  @GetMapping()
  public String handle_get() {
    return "redirect:/myposts";
  }

  @GetMapping("/{id}")
  public String handle_get_id(Model model, @PathVariable String id, Authentication au) {
    postService.isAuthorized(String.valueOf(getLoggedUser(au).getId()), id);
    model.addAttribute("loggedUser", userService.findByEmail(getLoggedUser(au).getUsername()));
    model.addAttribute("post", postService.findById(id));
    model.addAttribute("categories", categoryService.findAll());
    model.addAttribute("cities", cityService.getCities());
    return "a_u-post";
  }

  @PostMapping("/{id}")
  public String  handle_post(FormPost form,
                             @RequestParam("image") MultipartFile file,
                             @PathVariable String id,
                             Authentication au) {
    postService.addOrUpdate(
            String.valueOf(getLoggedUser(au).getId()),
            id,
            form.getName(),
            form.getCategory(),
            form.getCity(),
            form.getExpiryDate(),
            file);
    return ("redirect:/myposts");
  }

  UserrDetails getLoggedUser(Authentication authentication) {
    return (UserrDetails) authentication.getPrincipal();
  }

}
