package app.controller;

import app.entity.Message;
import app.form.FormChat;
import app.security.UserrDetails;
import app.service.BlockedService;
import app.service.MessageService;
import app.service.UserService;
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

import java.util.List;

@Log4j2
@Controller
@AllArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final BlockedService blockedService;

    @GetMapping()
    public String handle_get(Model model, Authentication au) {
        model.addAttribute("loggedUser", userService.findByEmail(getLoggedUser(au).getUsername()));
        model.addAttribute("connections",
                messageService.findLastMessagesByUser(getLoggedUser(au).getId()));
        return "chat-main";
    }

    @GetMapping("/{id}")
    public String handle_get(@PathVariable String id, Model model, Authentication au) {
        long loggedUserId = getLoggedUser(au).getId();

        blockedService.checkBlock(id, loggedUserId);

        List<Message> m = messageService.findMessagesBetween(loggedUserId, id);

        model.addAttribute("loggedUser", userService.findByEmail(getLoggedUser(au).getUsername()));
        model.addAttribute("currentUser", userService.findById(id));
        model.addAttribute("messages", m.subList(Math.max(m.size() - 5, 0), m.size()));
        return "chat-private";
    }

    @GetMapping("/all/{id}")
    public String handle_get_all(@PathVariable String id, Model model, Authentication au) {
        long loggedUserId = getLoggedUser(au).getId();

        blockedService.checkBlock(id, loggedUserId);

        model.addAttribute("loggedUser", userService.findByEmail(getLoggedUser(au).getUsername()));
        model.addAttribute("currentUser", userService.findById(id));
        model.addAttribute("messages", messageService.findMessagesBetween(loggedUserId, id));
        return "chat-private";
    }

    @PostMapping("/{id}")
    public RedirectView handle_post(FormChat form, @PathVariable String id, Authentication au) {
        messageService.sendMessage(String.valueOf(getLoggedUser(au).getId()), id, form.getMessage());
        return new RedirectView("{id}");
    }

    UserrDetails getLoggedUser(Authentication authentication) {
        return (UserrDetails) authentication.getPrincipal();
    }
}
