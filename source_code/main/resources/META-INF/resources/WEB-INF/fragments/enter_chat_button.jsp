<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<style>
#btn-enterchat {
    position: fixed;
    right: 15px;
    bottom: 70px;
    z-index: 1049;
    width: 60px;
    height: 60px;
    border-radius: 50%;
    font-size: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
    background-color: #dc3545;
    color: white;
    cursor: pointer;
    transition: transform 0.3s ease-in-out;
}

#btn-enterchat:hover {
    transform: scale(1.1);
}
</style>
<sec:authorize access="isAuthenticated()">
	<a id="btn-enterchat" href="/user/chatroom" class="btn btn-danger">
		<i class="bi bi-chat"></i>
	</a>
</sec:authorize>