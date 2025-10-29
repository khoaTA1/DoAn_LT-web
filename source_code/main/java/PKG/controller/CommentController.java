package PKG.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

// Import DTO mới
import PKG.model.CommentRequest;
import PKG.model.CommentResponse;
import PKG.service.CommentService;

@RestController
@RequestMapping("/api") 
public class CommentController {

    @Autowired
    private CommentService commentService;

    // === API 1: LẤY (GET) TẤT CẢ BÌNH LUẬN ===
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @RequestParam String type, 
            @RequestParam Long id) {    

        List<CommentResponse> comments = commentService.getCommentsByProduct(id, type);
        return ResponseEntity.ok(comments);
    }

    // === API 2: TẠO (POST) BÌNH LUẬN MỚI ===
    @PostMapping("/comments")
    public ResponseEntity<CommentResponse> createComment(
            @RequestBody CommentRequest request) {                 

    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
        	System.out.println("Lỗi không tìm thấy người dùng xác thực");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = auth.getName();
        System.out.println(username);
        
        CommentResponse newComment = commentService.createComment(request, username);
        
        System.out.println("Đã đánh giá");
        return ResponseEntity.status(HttpStatus.CREATED).body(newComment);
    }
}