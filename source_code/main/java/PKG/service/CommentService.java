package PKG.service;

import java.util.List;
import PKG.model.*;


public interface CommentService {
	// Lấy tất cả bình luận cho một sản phẩm
    List<CommentResponse> getCommentsByProduct(Long entityId, String entityType);

    // Tạo một bình luận mới
    CommentResponse createComment(CommentRequest request, String username);

}
