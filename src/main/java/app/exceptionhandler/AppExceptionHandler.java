package app.exceptionhandler;

import app.exception.input.*;
import app.exception.message.MessageNotFoundEx;
import app.exception.post.*;
import app.exception.user.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@ControllerAdvice
public class AppExceptionHandler {

  private String format(String s) {
    return String.format("AppExceptionHandler: %s", s);
  }

  @ExceptionHandler(UserNotFoundEx.class)
  public RedirectView handleUserNotFound(Model model, Exception ex) {
    model.addAttribute("error", "usernotfound");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/dashboard/1");
  }

  @ExceptionHandler(PostNotFoundEx.class)
  public RedirectView handlePostNotFound(Model model, Exception ex) {
    model.addAttribute("error", "postnotfound");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/dashboard/1");
  }

  @ExceptionHandler(EmailNotUniqueEx.class)
  public RedirectView handleEmailNotUnique(Model model, Exception ex) {
    model.addAttribute("error", "emailnotunique");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/signup");
  }

  @ExceptionHandler(UsernameNotUniqueEx.class)
  public RedirectView handleUsername(Model model, Exception ex) {
    model.addAttribute("error", "usernamenotunique");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/info");

  }

  @ExceptionHandler(PassNotMatchEx.class)
  public RedirectView handlePassNotMatch(Model model, Exception ex) {
    model.addAttribute("error", "passnotmatch");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/signup");
  }

  @ExceptionHandler(NewPassNotMatchEx.class)
  public RedirectView handleNewPassNotMatch(Model model, Exception ex) {
    model.addAttribute("error", "passnotmatch");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/resetpass");
  }

  @ExceptionHandler(EmptyInputEx.class)
  public RedirectView handleEmptyInput(Model model, Exception ex) {
    model.addAttribute("error", "emptyinput");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/signup");
  }

  @ExceptionHandler(ResetEmptyInputEx.class)
  public RedirectView handleResetEmptyInput(Model model, Exception ex) {
    model.addAttribute("error", "emptyinput");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/resetpass");
  }

  @ExceptionHandler(InvalidPhoneNumberEx.class)
  public RedirectView handleInvalidPhoneNumber(Model model, Exception ex) {
    model.addAttribute("error", "invalidphonenumber");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/signup");
  }

  @ExceptionHandler(IncorrectLoginEx.class)
  public RedirectView handleIncorrectLogin(Model model, Exception ex) {
    model.addAttribute("error", "incorrectlogin");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/signin");
  }

  @ExceptionHandler(NoPostEx.class)
  public RedirectView handleNoPost(Model model, Exception ex) {
    model.addAttribute("error", "nopost");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/myposts/1");
  }

  @ExceptionHandler(InvalidDateFormatEx.class)
  public RedirectView handleInvalidDateFormat(Model model, Exception ex) {
    model.addAttribute("error", "invaliddateformat");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/operation");
  }

  @ExceptionHandler(NoMatchedPostEx.class)
  public RedirectView handleMatchedPost(Model model, Exception ex) {
    model.addAttribute("error", "matchedpost");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/dashboard/1");
  }

  @ExceptionHandler(AdvEmptyInputEx.class)
  public RedirectView handleAdvEmptyInput(Model model, Exception ex) {
    model.addAttribute("error", "advemptyinput");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/index");
  }

  @ExceptionHandler(ChatEmptyInputEx.class)
  public RedirectView handleChatEmptyInput(Model model, Exception ex) {
    model.addAttribute("error", "chatemptyinput");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/chat");
  }

  @ExceptionHandler(FillInfoEmptyInputEx.class)
  public RedirectView handleFillInfoEmptyInput(Model model, Exception ex) {
    model.addAttribute("error", "fillinfoemptyinput");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/info");
  }

  @ExceptionHandler(PostEmptyInputEx.class)
  public RedirectView handlePostEmptyInput(Exception ex) {
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/myposts/1");
  }

  @ExceptionHandler(SignInEmptyInputEx.class)
  public RedirectView handleSignInEmptyInput(Model model, Exception ex) {
    model.addAttribute("error", "signinemptyinput");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/login");
  }

  @ExceptionHandler(SignUpEmptyInputEx.class)
  public RedirectView handleSignUpEmptyInput(Model model, Exception ex) {
    model.addAttribute("error", "signupemptyinput");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/signup");
  }

  @ExceptionHandler(UpdateUserEmptyInputEx.class)
  public RedirectView handleUpdateUserEmptyInput(Model model, Exception ex) {
    model.addAttribute("error", "updateuseremptyinput");
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/update");
  }

  @ExceptionHandler(MessageEmptyInputEx.class)
  public RedirectView handleMessageEmptyInput(Exception ex) {
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/message");
  }

  @ExceptionHandler(InvalidInputEx.class)
  public RedirectView handleInvalidInput(Exception ex) {
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/dashboard/1");
  }

  @ExceptionHandler(NoParamEx.class)
  public RedirectView handleNoParam(Exception ex) {
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/dashboard/1");
  }

  @ExceptionHandler(NotAuthorizedEx.class)
  public RedirectView handleNotAuthorized(Exception ex) {
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/myposts/1");
  }

  @ExceptionHandler(DuplicateWishedEx.class)
  public RedirectView handleDuplicateWished(Exception ex) {
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/wishlist");
  }

  @ExceptionHandler(DuplicateBlockedEx.class)
  public RedirectView handleDuplicateBlocked(Exception ex) {
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/dashboard/1");
  }

  @ExceptionHandler(NotWishedEx.class)
  public RedirectView handleNotWished(Exception ex) {
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/wishlist/1");
  }

  @ExceptionHandler(SelfViewEx.class)
  public RedirectView handleSelfView(Exception ex) {
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/update");
  }

  @ExceptionHandler(MessageNotFoundEx.class)
  public RedirectView handleMessageNotFound(Exception ex) {
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/dashboard/1");
  }

  @ExceptionHandler(InvalidResetTokenEx.class)
  public RedirectView handleInvalidResetToken(Exception ex) {
    log.warn(format(ex.getClass().getSimpleName()));
    return new RedirectView("/signin");
  }

  @ExceptionHandler(BlockedUserEx.class)
  public RedirectView handleBlockedUser(Model model, Exception ex) {
    log.warn(format(ex.getClass().getSimpleName()));
    model.addAttribute("error", "blocked");
    return new RedirectView("/dashboard/1");
  }


}
