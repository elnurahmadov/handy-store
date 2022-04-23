package app.service;

import app.entity.ResetToken;
import app.entity.Userr;
import app.exception.post.InvalidInputEx;
import app.exception.user.InvalidResetTokenEx;
import app.repo.ResetTokenRepo;
import app.tool.ValidationTool;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@AllArgsConstructor
@Service
public class ResetPasswordService {
  private final ResetTokenRepo resetTokenRepo;
  private final UserService userService;
  private final ValidationTool validationTool;

  public List<ResetToken> findAllByUser(Userr user) {
    return resetTokenRepo.findAllByUser(user);
  }

  public void addOrUpdateResetToken(String email, String token) {
    Userr user = userService.findByEmail(email);
    if (token == null || token.isBlank()) throw new InvalidInputEx();

    else if (findAllByUser(user).isEmpty()) {
      ResetToken resetToken = new ResetToken(user, token);
      resetTokenRepo.save(resetToken);
      log.info("Token added successfully");

    } else {
      ResetToken resetToken = findAllByUser(user).get(0);
      resetToken.setToken(token);
      resetTokenRepo.save(resetToken);
      log.info("Token updated successfully");
    }
  }

  public void deleteToken(String email) {
    Userr user = userService.findByEmail(email);
    resetTokenRepo.deleteByUser(user);
  }

  public void validateToken(String email, String token) {
    if (!validationTool.isTokenCorrect(email, token)) throw new InvalidResetTokenEx();
  }

}
