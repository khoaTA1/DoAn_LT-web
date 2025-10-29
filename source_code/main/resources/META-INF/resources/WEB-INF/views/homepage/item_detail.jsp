<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fm" uri="jakarta.tags.fmt"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<style>
/* style phần nút bấm yêu cầu đăng nhập */
.item-description {
    text-align: justify;
    white-space: pre-line;
    line-height: 1.6;
    font-size: 15px;
}

.custom-popup {
	display: none;
	position: fixed;
	z-index: 9999;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.5);
	backdrop-filter: blur(3px);
	justify-content: center;
	align-items: center;
	animation: fadeIn 0.3s ease;
}

.custom-popup-content {
	background: #fff;
	padding: 25px 30px;
	border-radius: 12px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	text-align: center;
	max-width: 350px;
	width: 90%;
	animation: slideUp 0.3s ease;
}

.popup-buttons {
	display: flex;
	justify-content: center;
	gap: 15px;
}

.btn-login {
	background: #dc3545;
	color: #fff;
	border: none;
	padding: 8px 18px;
	border-radius: 6px;
	text-decoration: none;
	font-weight: 500;
	transition: background 0.2s;
}

.btn-login:hover {
	background: #bb2d3b;
}

.btn-cancel {
	background: #6c757d;
	color: #fff;
	border: none;
	padding: 8px 18px;
	border-radius: 6px;
	font-weight: 500;
	cursor: pointer;
	transition: background 0.2s;
}

.btn-cancel:hover {
	background: #5a6268;
}

/* style hiển thị khung đánh giá */
.comment-box {
    border: 1px solid #ddd;
    padding: 20px;
    margin: 20px 0;
    border-radius: 8px;
    background-color: #fff;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.comment-box h5 {
    font-size: 18px;
    font-weight: bold;
    color: #333;
}

.comment-box h6 {
    font-size: 16px;
    margin-top: 10px;
    color: #555;
}

.comment-item {
    border: 1px solid #ddd;
    padding: 10px;
    margin: 10px 0;
    border-radius: 8px;
    background-color: #f9f9f9;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.comment-item strong {
    font-size: 16px;
    color: #333;
    font-weight: bold;
}

.comment-item p {
    margin-top: 5px;
    font-size: 14px;
    color: #555;
}

.comment-item .timestamp {
    font-size: 12px;
    color: #888;
    font-style: italic;
}

.comment-item .username {
    color: #dc3545;
}

@keyframes fadeIn {
	from { opacity: 0; }
	to { opacity: 1; }
}
@keyframes slideUp {
	from { transform: translateY(20px); opacity: 0; }
	to { transform: translateY(0); opacity: 1; }
}
</style>
<div>
	<%
	Object item = request.getAttribute("itemdetail");
	java.lang.reflect.Field[] fields = item.getClass().getDeclaredFields();
	java.lang.reflect.Field[] parentFields = item.getClass().getSuperclass().getDeclaredFields();

	Map<String, String> fieldNames = (Map<String, String>) request.getAttribute("fieldNames");
	Map<String, String> trueOrfalseRewrite = (Map<String, String>) request.getAttribute("trueOrFalseRewrite");

	java.util.List<String> hiddenFields = java.util.Arrays.asList("id", "categoryId", "price", "image", "discount", "description");
	%>

	<div class="card">
		<!-- Ảnh minh họa -->
		<img src="/image?fname=${itemdetail.image}" class="card-img-top" />

		<div class="card-body mb-3">
			<!-- Tên sản phẩm -->
			<h3 class="card-title text-danger mb-3">
				<%=item.getClass().getMethod("getName").invoke(item)%>
			</h3>
			
			<h5>Mô tả sản phẩm</h5>
			<p class="item-description" >${itemdetail.description}</p>

			<h5>Thông tin sản phẩm</h5>
			<table class="table table-striped">
				<tbody>
					<%
					// Hiển thị các thuộc tính của lớp cha trước (Item)
					for (java.lang.reflect.Field field : parentFields) {
						if (hiddenFields.contains(field.getName()))
							continue;
						field.setAccessible(true);
						Object value = field.get(item);
						if (value != null) {
					%>
					<tr>
						<th><%=fieldNames.getOrDefault(field.getName(), field.getName())%></th>
						<td><%=trueOrfalseRewrite.getOrDefault(value.toString(), value.toString())%></td>
					</tr>
					<%
					}
					}
					// Hiển thị thuộc tính của lớp con
					for (java.lang.reflect.Field field : fields) {
					field.setAccessible(true);
					Object value = field.get(item);
					if (value != null) {
					%>
					<tr>
						<th><%=fieldNames.getOrDefault(field.getName(), field.getName())%></th>
						<td><%=trueOrfalseRewrite.getOrDefault(value.toString(), value.toString())%></td>
					</tr>
					<%
					}
					}
					%>
				</tbody>
			</table>
		</div>
	</div>
</div>

<!-- đánh giá sản phẩm -->
<div class="comment-box">
	<h5>Đánh giá sản phẩm</h5>
	<!-- hiển thị các đánh giá -->
	<div id="comments-list"></div>

	<!-- Form thêm đánh giá -->
	<h6>Thêm đánh giá của bạn:</h6>
	<form id="comment-form">
		<div class="mb-3">
			<textarea class="form-control" id="comment-text" rows="3"
				placeholder="Nhập đánh giá của bạn"></textarea>
		</div>
		<!-- nếu người dùng đã đăng nhập -->
		<sec:authorize access="isAuthenticated()">
			<button type="submit" class="btn btn-danger">Gửi đánh giá</button>
		</sec:authorize>

		<!-- nếu người dùng chưa đăng nhập -->
		<sec:authorize access="!isAuthenticated()">
			<button type="button" onclick="showAlertLoginPopup()"
				class="btn btn-danger">Gửi đánh giá</button>
		</sec:authorize>
	</form>
</div>

<div id="alertLoginPopup" class="custom-popup">
	<div class="custom-popup-content">
		<h5>Có vẻ bạn chưa đăng nhập?</h5>
		<p>Bạn cần đăng nhập để tiếp tục.</p>
		<div class="popup-buttons">
			<a href="/redirect/login" class="btn-login">Đăng nhập</a>
			<button class="btn-cancel" onclick="closeAlertLoginPopup()">Hủy</button>
		</div>
	</div>
</div>

<script>
	function showAlertLoginPopup() {
		document.getElementById('alertLoginPopup').style.display = 'flex';
	}

	function closeAlertLoginPopup() {
		document.getElementById('alertLoginPopup').style.display = 'none';
	}

	const entityType = '${catename}';
    const entityId = '${itemdetail.id}';

    // Hàm tải các bình luận từ API
    function loadComments() {
        fetch('/api/comments?type=' + entityType + '&id=' + entityId)
            .then(response => response.json())
            .then(comments => {
                const commentsList = document.getElementById('comments-list');
                commentsList.innerHTML = ''; // Xóa nội dung cũ

                comments.forEach(comment => {
                    const commentElement = document.createElement('div');
                    commentElement.classList.add('comment-item');
                    const createdAt = new Date(comment.createdAt);
                    const formattedDate = createdAt.toLocaleString('vi-VN', {
                        weekday: 'long',
                        year: 'numeric',
                        month: 'long',
                        day: 'numeric',
                        hour: '2-digit',
                        minute: '2-digit',
                        second: '2-digit'
                    });
                    commentElement.innerHTML = '<strong>' + comment.username + ' - ' + formattedDate + '</strong> <p>' + comment.content + '</p>';
                    commentsList.appendChild(commentElement);
                });
            })
            .catch(error => {
                console.error('Có lỗi khi tải bình luận:', error);
            });
    }
	
    // Gửi bình luận mới tới API
    document.getElementById('comment-form').addEventListener('submit', function (event) {
        event.preventDefault();
        const commentText = document.getElementById('comment-text').value;
        
        if (!commentText) {
            alert('Vui lòng nhập bình luận!');
            return;
        }

        const JWTToken = sessionStorage.getItem("jwtToken");
	 		if (!JWTToken) {
    		alert("Lỗi xác thực JWT, vui lòng thử lại!");
    		return;
		}
		const headervalue = "Bearer " + JWTToken;
	
        fetch('/api/comments', {
            method: 'POST',
            headers: {
            	'Authorization': headervalue,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 'content': commentText, 'entityId': entityId, 'entityType': entityType })
        })
        .then(response => response.json())
        .then(newComment => {
            // Thêm bình luận mới vào danh sách hiển thị
            loadComments();
            document.getElementById('comment-text').value = '';  // Xóa nội dung trong textarea
        })
        .catch(error => {
            console.error('Có lỗi khi gửi bình luận:', error);
        });
    });

    // Tải bình luận khi trang được tải
    window.onload = function() {
        loadComments();
    };
</script>
