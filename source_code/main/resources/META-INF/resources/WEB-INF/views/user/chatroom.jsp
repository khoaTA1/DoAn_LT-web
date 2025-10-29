<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
<title>Điện máy đỏ</title>
<link rel="stylesheet" href="/css/main.css" />
</head>
<body>
	<div id="username-page">
		<div class="username-page-container">
			<div class="text-center mb-4">
				<a href="/homepage" id="btn-homeredirect"
					style="text-decoration: none;">ĐIỆN MÁY ĐỎ</a>
			</div>
			<h1 class="title">Nhập tên hiển thị</h1>
			<form id="usernameForm" name="usernameForm">
				<div class="form-group">
					<input type="text" id="name" placeholder="Username"
						autocomplete="off" class="form-control" />
				</div>
				<div class="form-group">
					<button type="submit" class="accent username-submit">Bắt
						đầu</button>
				</div>
			</form>
		</div>
	</div>

	<div id="chat-page" class="hidden">
		<div class="chat-container">
			<div class="chat-header">
				<h2>Kênh tin nhắn chung Điện Máy Đỏ</h2>
			</div>
			<div class="connecting">Đang kết nối...</div>
			<ul id="messageArea">

			</ul>
			<form id="messageForm" name="messageForm">
				<div class="form-group">
					<div class="input-group clearfix">
						<input type="text" id="message" placeholder="Nhập tin nhắn..."
							autocomplete="off" class="form-control" />
						<button type="submit" class="primary">Gửi</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
	<script src="/js/main.js"></script>
</body>
</html>
