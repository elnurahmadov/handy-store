package app.controller;

import app.entity.Post;
import app.security.UserrDetails;
import app.service.PostService;
import app.service.UserService;
import app.tool.PaginationTool;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Log4j2
@Controller
@AllArgsConstructor
@RequestMapping("/myposts")
public class PostViewController {

  private final PostService postService;
  private final UserService userService;
  private final PaginationTool<Post> paginationTool;

  /**
   * http://localhost:5000/myposts/2?sortField=name&sortDir=asc
   */
  @RequestMapping()
  public RedirectView handle_get() {
    return new RedirectView("/myposts/1");
  }

  @GetMapping("/{currentPage}")
  public String handle_get(Model model, Authentication au,
                           @PathVariable("currentPage") Optional<Integer> currentPageOp,
                           @RequestParam("sortField") Optional<String> sortFieldOp,
                           @RequestParam("sortDir") Optional<String> sortDirOp) {

    int currentPage = currentPageOp.orElse(1);
    String sortField = sortFieldOp.orElse("name");
    String sortDir = sortDirOp.orElse("asc");
    Page<Post> page = postService.findByUser(String.valueOf(getLoggedUser(au).getId()),
            currentPage, sortField, sortDir);

    paginationTool.controller(page, model, currentPage, sortField, sortDir);

    model.addAttribute("loggedUser", userService.findByEmail(getLoggedUser(au).getUsername()));
    model.addAttribute("posts", page);
    return "manage-post";
  }


  @GetMapping("/delete/{id}")
  public RedirectView handle_get(@PathVariable String id, Authentication au) {
    postService.isAuthorized(String.valueOf(getLoggedUser(au).getId()), id);
    postService.deactivate(id);
    return new RedirectView("/myposts");
  }

  @PostMapping("/{currentPage}")
  public String handle_post() {
    return "manage-post";
  }

  UserrDetails getLoggedUser(Authentication authentication) {
    return (UserrDetails) authentication.getPrincipal();
  }

}
