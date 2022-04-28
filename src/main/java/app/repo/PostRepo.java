package app.repo;


import app.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends PagingAndSortingRepository<Post, Long> {
    Optional<Post> findById(Long id);
//
    @Query("SELECT post from Post as post where post.user.id = :id")
    Post findByUserId(Long id);

    Page<Post> findAllByStatusAndIdIsNot(boolean status, long id, Pageable pageable);

    Page<Post> findAllByNameContainingIgnoreCaseAndCategory_IdAndStatus(String name, Long categoryId, boolean status, Pageable pageable);

    Page<Post> findPostsByUserIdAndStatus(long userId, boolean status, Pageable pageable);

}