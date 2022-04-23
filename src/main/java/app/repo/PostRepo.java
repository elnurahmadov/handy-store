package app.repo;


import app.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends PagingAndSortingRepository<Post, Long> {

    Page<Post> findAllByStatusAndIdIsNot(boolean status, long id, Pageable pageable);

    Page<Post> findAllByNameContainingIgnoreCaseAndCategory_IdAndStatus(String name, Long categoryId, boolean status, Pageable pageable);

    Page<Post> findPostsByUserIdAndStatus(long userId, boolean status, Pageable pageable);

}
