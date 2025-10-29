package PKG.service.impl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PKG.model.CommentRequest;
import PKG.model.CommentResponse;
import PKG.entity.Comment;
import PKG.entity.User;
import PKG.repository.CommentRepo;
import PKG.repository.UserRepo;
import PKG.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo; // Cần UserRepo để tìm user theo username
    
	@Override
	public List<CommentResponse> getCommentsByProduct(Long entityId, String entityType) {
		// 1. Gọi Repo
		List<Comment> comments = commentRepo.findByEntityIdAndEntityTypeOrderByCreatedAtDesc(entityId, entityType);
	
		// 2. Chuyển đổi sang List<CommentRespone>
        return comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
	}

	@Override
	public CommentResponse createComment(CommentRequest request, String username) {
		// 1. Tìm user
        User user = userRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        // 2. Tạo entity Comment
        Comment newComment = new Comment();
        newComment.setContent(request.getContent());
        newComment.setEntityId(request.getEntityId());
        newComment.setEntityType(request.getEntityType());
        newComment.setUser(user);

        // 3. Lưu vào DB
        Comment savedComment = commentRepo.save(newComment);

        // 4. Chuyển đổi sang CommentRespone
        return convertToDTO(savedComment);
	}
	
	// Hàm tiện ích private để chuyển đổi Entity -> DTO (Model)  mới
     private CommentResponse convertToDTO(Comment comment) {
        CommentResponse dto = new CommentResponse();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUsername(comment.getUser().getUserName()); 
        return dto;
    }

	
}
