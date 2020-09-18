function chkLength() {
	if(frm.user_id.value.length <= 0) {
		alert('아이디를 입력해주세요!');
		frm.user_id.focus();
		return false;
	} else if(frm.user_pw.value.length <= 0) {
		alert('비밀번호를 입력해주세요!')
		frm.user_pw.focus();
		return false;		
	} else if(frm.user_pwre.value.length <= 0) {
		alert('비밀번호 확인란을 입력해주세요!')
		frm.user_pwre.focus();
		return false;
	} else if(frm.nm.value.length <= 0) {
		alert('이름을 입력해주세요!')
		frm.nm.focus();
		return false;
	} else if(frm.nm.value.length > 0) {
		const korean = /[^가-힣]/;
		if(korean.test(frm.nm.value)) {
			alert('이름은 한글만 입력해주세요.');
			frm.nm.focus();
			return false;
		}
	}
	
	if(frm.user_pw.value !== frm.user_pwre.value) {
		alert('비밀번호가 같지 않습니다!');
		frm.user_pwre.focus();
		return false;
	}
	return true;
}
