package app.service;

import app.entity.Message;
import app.entity.Userr;
import app.exception.input.MessageEmptyInputEx;
import app.exception.post.InvalidInputEx;
import app.repo.MessageRepo;
import app.tool.ValidationTool;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class MessageService {
  private final ValidationTool validationTool;
  private final MessageRepo messageRepo;
  private final UserService userService;

  public List<Message> findMessagesBetween(long loggedUserId, String currentUserId) {

    Userr loggedUser = userService.findById(String.valueOf(loggedUserId));
    Userr currentUser = userService.findById(currentUserId);

    List<Message> allMessages = new ArrayList<>();

    allMessages.addAll(messageRepo.findAllByFromAndTo(loggedUser, currentUser));
    allMessages.addAll(messageRepo.findAllByFromAndTo(currentUser, loggedUser));
    allMessages.sort(Comparator.comparing(Message::getId));

    return allMessages;
  }

  public List<Message> findLastMessagesByUser(long loggedUserId) {
    Userr loggedUser = userService.findById(String.valueOf(loggedUserId));

    return messageRepo.findAllByFromOrTo(loggedUser, loggedUser)
            .stream()
            .map(m -> m.getFrom().equals(loggedUser) ? m.getTo() : m.getFrom())
            .distinct()
            .map(c -> findMessagesBetween(loggedUserId, String.valueOf(c.getId())))
            .map(messages -> messages.get(messages.size() - 1))
            .collect(Collectors.toList());
  }

  public void sendMessage(String loggedUserId, String currentUserId, String text) {
    if (currentUserId == null || text == null || text.isBlank()) throw new MessageEmptyInputEx();
    if (!validationTool.isParsableToLong(currentUserId)) throw new InvalidInputEx();

    messageRepo.save(
            new Message(
                    userService.findById(loggedUserId),
                    userService.findById(currentUserId),
                    text,
                    LocalDateTime.now()));
  }

}
