package app.service;

import app.entity.Post;
import app.entity.Wished;
import app.exception.post.DuplicateWishedEx;
import app.exception.post.InvalidInputEx;
import app.exception.post.NotWishedEx;
import app.exception.post.PostNotFoundEx;
import app.repo.WishedRepo;
import app.tool.ValidationTool;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
@Service
public class WishedService {
  private final WishedRepo wishedRepo;
  private final PostService postService;
  private final ValidationTool validationTool;

  public List<Post> findWishedPosts(String userId) {
    return wishedRepo.findAllByUserr(Long.parseLong(userId)).stream()
            .map(w -> postService.findById(String.valueOf(w.getPost())))
            .collect(Collectors.toList());
  }

  public long findCurrentWishedPostId(String userId, String postId) {
    if (!validationTool.isParsableToLong(postId)) throw new InvalidInputEx();
    return wishedRepo.findByUserrAndPost(Long.parseLong(userId), Long.parseLong(postId))
            .orElseThrow(PostNotFoundEx::new).getPost();
  }

  public List<Post> findFilteredWishedPosts(String userId, String name, String category) {
    return findWishedPosts(userId).stream()
            .filter(w -> w.getName().toLowerCase().contains(name.toLowerCase())
                    && w.getCategory().getId() == Long.parseLong(category))
            .collect(Collectors.toList());
  }

  public void addWished(String userId, String postId) {
    if (!validationTool.isParsableToLong(postId)) throw new InvalidInputEx();
    if (findWishedPosts(userId).stream()
            .anyMatch(w -> w.getId() == Long.parseLong(postId))) throw new DuplicateWishedEx();

    wishedRepo.save(new Wished(Long.parseLong(userId), Long.parseLong(postId)));
  }

  public void deleteWished(String userId, String postId) {
    if (!validationTool.isParsableToLong(postId)) throw new InvalidInputEx();
    if (findWishedPosts(userId).stream()
            .noneMatch(w -> w.getId() == Long.parseLong(postId))) throw new NotWishedEx();

    wishedRepo.deleteByPostAndUserr(Long.parseLong(postId), Long.parseLong(userId));
  }

}
