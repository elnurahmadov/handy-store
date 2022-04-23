package app.service;

import app.entity.Userr;
import app.exception.input.*;
import app.exception.post.InvalidInputEx;
import app.exception.user.*;
import app.repo.UserRepo;
import app.tool.FileTool;
import app.tool.ValidationTool;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Service
public class UserService {
  private final ValidationTool validationTool;
  private final FileTool fileTool;
  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;
  private final CloudinaryService cloudinaryService;

  public void register(String email, String pass, String passConfirm) {
    if (email == null || pass == null || passConfirm == null
            || email.isBlank() || pass.isBlank() || passConfirm.isBlank()) throw new SignUpEmptyInputEx();
    if (!validationTool.isEmailUnique(email)) throw new EmailNotUniqueEx();
    if (!validationTool.passMatches(pass, passConfirm)) throw new PassNotMatchEx();

    Userr user = new Userr(email.toLowerCase(), passwordEncoder.encode(pass), LocalDateTime.now(), true);
    user.setName("Name");
    user.setSurname("");
    user.setImage("/img/profile/user.png");
    user.setCity("Baku");
    userRepo.save(user);

    roleService.addRoleToUser(user, "USER");
    log.info("User registered successfully");
  }

  public void login(String login, String pass) {
    if (!validationTool.isLoginCorrect(login, pass))
      throw new IncorrectLoginEx();
  }

  public boolean isInfoFilled(long loggedUserId) {
    Userr user = findById(String.valueOf(loggedUserId));
    return user.getCity() != null && user.getName() != null &&
            user.getSurname() != null && !user.getSurname().isBlank();
  }

  public void fillInfo(String id, String name, String surname, String city, String number, MultipartFile file) {
    if (name == null || surname == null || city == null
            || number == null || id.isBlank() || name.isBlank()
            || surname.isBlank() || city.isBlank() || number.isBlank()
            || file.isEmpty()
    ) throw new FillInfoEmptyInputEx();

    if (validationTool.isPhoneValid(number)) throw new InvalidPhoneNumberEx();

//    String image = "/" + fileTool.uploadProfilePic(file,
//            String.format("%s%s", name, surname)
//                    .replaceAll(" ", "")
//                    .toLowerCase());

    String image = cloudinaryService.uploadFile(file);

    Userr user = findById(id);
    user.setName(name);
    user.setSurname(surname);
    user.setCity(city);
    user.setPhone(number);
    user.setImage(image);
    userRepo.save(user);
    log.info("User info filled successfully");
  }

  public void updateUser(String id, String name, String surname, String city, String number, MultipartFile file) {
    if (name == null || surname == null || city == null
            || number == null || id.isBlank() || name.isBlank()
            || surname.isBlank() || city.isBlank() || number.isBlank()
            || file.isEmpty()
    ) throw new UpdateUserEmptyInputEx();

    if (validationTool.isPhoneValid(number)) throw new InvalidPhoneNumberEx();

//    String image = "/" + fileTool.uploadProfilePic(file,
//            String.format("%s%s", name, surname)
//                    .replaceAll(" ", "")
//                    .toLowerCase());

    String image = cloudinaryService.uploadFile(file);

    Userr user = findById(id);
    user.setName(name);
    user.setSurname(surname);
    user.setCity(city);
    user.setPhone(number);
    user.setImage(image);
    userRepo.save(user);
    log.info("User profile updated successfully");
  }

  public void updatePassword(String email, String pass, String conPass) {
    if (email == null || pass == null || conPass == null
            || email.isBlank() || pass.isBlank() || conPass.isBlank()) throw new ResetEmptyInputEx();

    if (!pass.equals(conPass)) throw new NewPassNotMatchEx();

    Userr user = findByEmail(email);
    user.setPassword(passwordEncoder.encode(pass));
    userRepo.save(user);
  }

  public Userr findById(String id) {
    if (!validationTool.isParsableToLong(id)) throw new InvalidInputEx();
    return userRepo.findById(Long.parseLong(id)).orElseThrow(UserNotFoundEx::new);
  }

  public Userr findByEmail(String email) {
    return userRepo.findUserrByEmail(email).orElseThrow(UserNotFoundEx::new);
  }

  public Optional<Userr> findUserForLogin(String email) {
    return userRepo.findUserrByEmail(email);
  }

  public Userr viewUser(String userId, long loggedId) {
    Userr user = findById(userId);
    if (user.getId() == loggedId) throw new SelfViewEx();
    return user;
  }

  public boolean isUserExistByEmail(String mail) {
    return userRepo.findUserrByEmail(mail).isPresent();
  }
}
