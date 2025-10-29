package PKG.model;
import java.time.LocalDateTime;
import lombok.Data;

//Dùng để trả bình luận về cho frontend hiển thị
@Data
public class CommentResponse {
	private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String username;
    
    
    
    
	
	
	

}
