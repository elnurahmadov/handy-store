package app.controller;

import app.entity.Post;
import app.exception.input.NoParamEx;
import app.form.FormSearch;
import app.security.UserrDetails;
import app.service.CategoryService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Log4j2
@Controller
@AllArgsConstructor
@RequestMapping("/search")
public class SearchController {

  private final PostService postService;
  private final CategoryService categoryService;
  private final UserService userService;
  private final PaginationTool<Post> paginationTool;

  @GetMapping("/{currentPage}")
  public String handle_get(HttpServletRequest req, Model model, Authentication au,
                           @PathVariable("currentPage") Optional<Integer> currentPageOp,
                           @RequestParam("sortField") Optional<String> sortFieldOp,
                           @RequestParam("sortDir") Optional<String> sortDirOp ) {

    String name = req.getParameter("name");
    String category = req.getParameter("cat");

    if (name == null || category ==null) throw new NoParamEx();

    int currentPage = currentPageOp.orElse(1);
    String sortField = sortFieldOp.orElse("name");
    String sortDir = sortDirOp.orElse("asc");

    Page<Post> page = postService.findFiltered(name, category,currentPage, sortField, sortDir);

    paginationTool.controller(page, model, currentPage, sortField, sortDir);

    model.addAttribute("loggedUser", userService.findByEmail(getLoggedUser(au).getUsername()));
    model.addAttribute("categories", categoryService.findAll());
    return "dashboard";
  }

  @PostMapping("/{currentPage}")
  public RedirectView handle_post(Model model, FormSearch form) {
    model.addAttribute("name", form.getKeyword());
    model.addAttribute("cat", form.getCategory());
    return new RedirectView("/search/1");
  }

  UserrDetails getLoggedUser(Authentication authentication) {
    return (UserrDetails) authentication.getPrincipal();
  }

}
