package app.service;

import app.entity.Category;
import app.entity.Post;
import app.exception.post.NotAuthorizedEx;
import app.exception.input.PostEmptyInputEx;
import app.exception.post.InvalidInputEx;
import app.exception.post.NoPostEx;
import app.exception.post.PostNotFoundEx;
import app.repo.CategoryRepo;
import app.repo.PostRepo;
import app.tool.ConverterTool;
import app.tool.FileTool;
import app.tool.PaginationTool;
import app.tool.ValidationTool;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;

@Log4j2
@Service
@AllArgsConstructor
public class PostService {
  private final PostRepo postRepo;
  private final CategoryRepo categoryRepo;
  private final UserService userService;
  private final ValidationTool validationTool;
  private final FileTool fileTool;
  private final ConverterTool converterTool;
  private final PaginationTool<Post> paginationTool;
  private final CloudinaryService cloudinaryService;


  public Page<Post> findAll(int currentPage, String sortField, String sortDir) {
    Pageable pageable = paginationTool.service(currentPage, sortField, sortDir);

    Page<Post> allPosts = postRepo.findAllByStatusAndIdIsNot(true, 0, pageable);

    if (allPosts.getTotalElements() == 0) {
      throw new NoPostEx();
    } else {
      return allPosts;
    }
  }

  public Post findById(String id) {
    if (validationTool.isParsableToLong(id))
      return postRepo.findById(Long.parseLong(id)).orElseThrow(PostNotFoundEx::new);
    else throw new InvalidInputEx();

  }

  public void deactivate(String id) {
    if (validationTool.isParsableToLong(id)) {
      Post post = postRepo.findById(Long.parseLong(id)).orElseThrow(PostNotFoundEx::new);
      post.setStatus(false);
      postRepo.save(post);
      log.info("Post deactivated successfully");
    } else {
      throw new InvalidInputEx();
    }
  }

  public void addOrUpdate(String userId, String postId, String name,
                          String category, String city, String date, MultipartFile file) {

    if (postId.isBlank() || name.isBlank() || category.isBlank()
            || city.isBlank() || date.isBlank() || file.isEmpty())
      throw new PostEmptyInputEx();
    if (!validationTool.isParsableToLong(postId) || !validationTool.isCategoryValid(category))
      throw new InvalidInputEx();

    LocalDate parsedDate = converterTool.stringToLocalDate(date);
//    String image = "/" + fileTool.uploadPostImage(file, name);
    String image = cloudinaryService.uploadFile(file);
    Category cat = categoryRepo.findById(Long.parseLong(category)).get();
    Post post;

    if (postId.equals("0")) {
      post = new Post(userService.findById(userId), name, cat, city, image, parsedDate);
      postRepo.save(post);
      log.info("Post added successfully");
    } else {
      post = findById(postId);
      post.setName(name);
      post.setCategory(cat);
      post.setCity(city);
      post.setExpiry_date(parsedDate);
      post.setImage(image);
      postRepo.save(post);
      log.info("Post updated successfully");
    }

  }

  public Page<Post> findFiltered(String name, String category, int currentPage, String sortField, String sortDir) {
    if (!validationTool.isParsableToLong(category)) {
      throw new InvalidInputEx();
    }

    Pageable pageable = paginationTool.service(currentPage, sortField, sortDir);
    Page<Post> filteredPosts = postRepo.findAllByNameContainingIgnoreCaseAndCategory_IdAndStatus(
            name, Long.parseLong(category), true, pageable);

    if (filteredPosts.getTotalElements() == 0) {
      throw new PostNotFoundEx();
    }

    return filteredPosts;
  }

  public Page<Post> findByUser(String userId, int currentPage, String sortField, String sortDir) {
    Pageable pageable = paginationTool.service(currentPage, sortField, sortDir);
    return postRepo.findPostsByUserIdAndStatus(Long.parseLong(userId), true, pageable);
  }

  public void isAuthorized(String userId, String postId) {
    if (!validationTool.isParsableToLong(postId)) throw new InvalidInputEx();
    if (Integer.parseInt(postId) == 0) return;
    if (findById(postId).getUser().getId() == Long.parseLong(userId)) return;
    throw new NotAuthorizedEx();
  }
}
