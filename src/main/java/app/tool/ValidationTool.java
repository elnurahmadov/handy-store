package app.tool;

import app.entity.Userr;
import app.exception.input.ResetEmptyInputEx;
import app.exception.user.UserNotFoundEx;
import app.repo.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Service
public class ValidationTool {
  private final UserRepo userRepo;
  private final CategoryRepo categoryRepo;
  private final ResetTokenRepo resetTokenRepo;
  private final BlockedRepo blockedRepo;

  public boolean isEmailUnique(String email) {
    return userRepo.findUserrByEmail(email.toLowerCase())
            .equals(Optional.empty());
  }

  public boolean passMatches(String pass, String passConfirm) {
    return pass.equals(passConfirm);
  }

  public boolean isPhoneValid(String number) {
    return !isAzercell(number) && !isBakcell(number) && !isNar(number) && !isHome(number);
  }

  public boolean isAzercell(String number) {
    try {
      long parsedNumber = Long.parseLong(number);
      return (parsedNumber > 502000000 && parsedNumber < 509999999)
              || (parsedNumber > 512000000 && parsedNumber < 519999999);
    } catch (NumberFormatException e) {
      log.warn("Number could not be parsed: from ValidationTool.isAzercell()");
      return false;
    }
  }

  public boolean isBakcell(String number) {
    try {
      long parsedNumber = Long.parseLong(number);
      return (parsedNumber > 552000000 && parsedNumber < 559999999)
              || (parsedNumber > 992000000 && parsedNumber < 999999999);
    } catch (NumberFormatException e) {
      log.warn("Number could not be parsed: from ValidationTool.isBakcell()");
      return false;
    }
  }

  public boolean isNar(String number) {
    try {
      long parsedNumber = Long.parseLong(number);
      return (parsedNumber > 702000000 && parsedNumber < 709999999)
              || (parsedNumber > 772000000 && parsedNumber < 779999999);
    } catch (NumberFormatException e) {
      log.warn("Number could not be parsed: from ValidationTool.isNar()");
      return false;
    }
  }

  public boolean isHome(String number) {
    try {
      long parsedNumber = Long.parseLong(number);
      return parsedNumber > 122000000 && parsedNumber < 129999999;
    } catch (NumberFormatException e) {
      log.warn("Number could not be parsed: from ValidationTool.isHome()");
      return false;
    }
  }

  public boolean isLoginCorrect(String login, String pass) {
    return userRepo.findUserrByEmail(login.toLowerCase())
            .orElseThrow(UserNotFoundEx::new)
            .getPassword()
            .equals(pass);
  }

  public boolean isParsableToLong(String source) {
    try {
      Long.parseLong(source);
      return true;
    } catch (NumberFormatException e) {
      e.printStackTrace();
      log.warn("Value could not be parsed to Long: from ValidationTool.isParsableToLong()");
      return false;
    }
  }

  public boolean isCategoryValid(String category) {
    if (!isParsableToLong(category)) return false;
    return categoryRepo.findAll().stream()
            .anyMatch(c -> c.getId() == Long.parseLong(category));
  }

  public boolean isTokenCorrect(String email, String token) {
    if (email == null || token == null || email.isBlank() || token.isBlank())
      throw new ResetEmptyInputEx();

    Userr user = userRepo.findUserrByEmail(email)
            .orElseThrow(UserNotFoundEx::new);

    return resetTokenRepo.findAllByUser(user)
            .get(0)
            .getToken()
            .equals(token);
  }

  public boolean isBlocked(String whoId, long whomId) {
    if (!isParsableToLong(whoId)) return false;

    return blockedRepo.findByWhoAndWhom(
            userRepo.findById(Long.parseLong(whoId)).orElseThrow(UserNotFoundEx::new),
            userRepo.findById(whomId).orElseThrow(UserNotFoundEx::new))
            .isPresent();
  }
}