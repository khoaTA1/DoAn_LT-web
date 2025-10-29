package PKG.model;

import lombok.Data;


//Dùng để nhận yêu cầu tạo bình luận từ frontend ( tạo mới 1 bình luận cho sản phẩm ) 
@Data
public class CommentRequest {
	// Nội dung bình luận
    private String content;

    // ID của sản phẩm 
    private Long entityId;

    // Loại sản phẩm 
    private String entityType;

}
