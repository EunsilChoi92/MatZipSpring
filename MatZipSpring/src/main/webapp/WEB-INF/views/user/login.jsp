<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sectionContainerCenter">
	<div>
		<div class="msg">${data.msg}</div>
		<form id="frm" class="frm" action="/user/login" method="post" onsubmit="return chkLength();">
			<div><input type="text" name="user_id" placeholder="아이디" value="${data.user_id}"></div>
			<div><input type="password" name="user_pw" placeholder="비밀번호"></div>
			<div><input type="submit" value="로그인"></div>
		</form>
		<a href="/user/join"><button>회원가입</button></a>
	</div>
</div>