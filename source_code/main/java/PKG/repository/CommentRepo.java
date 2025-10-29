package PKG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import PKG.entity.*;
import java.util.List;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepo extends JpaRepository<Comment, Long>{
	// Query : "SELECT * FROM comments 
    //            WHERE entity_id = ? AND entity_type = ? 
    //            ORDER BY created_at DESC"
	List<Comment> findByEntityIdAndEntityTypeOrderByCreatedAtDesc(Long entityId, String entityType);

}
