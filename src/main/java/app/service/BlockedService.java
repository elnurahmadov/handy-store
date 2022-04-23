package app.service;

import app.entity.Blocked;
import app.entity.Userr;
import app.exception.post.InvalidInputEx;
import app.exception.user.BlockedUserEx;
import app.exception.user.DuplicateBlockedEx;
import app.repo.BlockedRepo;
import app.tool.ValidationTool;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
@Service
public class BlockedService {
  private final BlockedRepo blockedRepo;
  private final UserService userService;
  private final ValidationTool validationTool;

  public void addBlocked(String loggedUserId, String currentUserId) {
    if (findBlockedUsers(loggedUserId).contains(userService.findById(currentUserId))) throw new DuplicateBlockedEx();
    if (loggedUserId.equals(currentUserId)) throw new InvalidInputEx();
    blockedRepo.save(new Blocked(userService.findById(loggedUserId), userService.findById(currentUserId)));
  }

  public List<Userr> findBlockedUsers(String loggedUserId) {
    return blockedRepo.findByWho(userService.findById(loggedUserId))
            .stream()
            .map(Blocked::getWhom)
            .collect(Collectors.toList());
  }

  public void checkBlock(String currentUserId, long loggedUserId) {
    if (validationTool.isBlocked(currentUserId, loggedUserId))
      throw new BlockedUserEx();
  }

}
