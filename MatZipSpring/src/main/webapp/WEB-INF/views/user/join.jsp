<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sectionContainerCenter">
	<div>
		<h1>회원가입</h1>
		<div class="msg">${msg}</div>
		<form id="frm" class="frm" action="/user/join" method="post" onsubmit="return chkLength();">
			<div id="idChkResult" class="msg"></div>
			<div><input type="text" name="user_id" placeholder="아이디">
				<button type="button" onclick="chkId()">아이디 중복 체크</button>
			</div>
			<div><input type="password" name="user_pw" placeholder="비밀번호"></div>
			<div><input type="password" name="user_pwre" placeholder="비밀번호 확인"></div>
			<div><input type="text" name="nm" placeholder="이름"></div>
			<div><input type="submit" value="회원가입"></div>
		</form>
		<div><a href="/user/login"><button>로그인</button></a></div>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<script>
		function chkId() {
			if(frm.user_id.value == '') {
				alert('아이디를 입력해주세요!');
				frm.user_id.focus();
				return;
			}
			const user_id = frm.user_id.value;
			axios.post('/user/ajaxIdChk', {
				user_id
			}).then(function(res) {
				console.log(res);
				if(res.data == '2') {
					idChkResult.innerText = '사용할 수 있는 아이디입니다.';
				} else if(res.data == '3') {
					idChkResult.innerText = '이미 사용중인 아이디입니다.';
				}
			})
		}
	</script>
</div>