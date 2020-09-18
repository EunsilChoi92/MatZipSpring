package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Service
public class UserService {
	@Autowired
	private UserMapper mapper;
	
	// 1번 로그인 성공, 2번 아이디 없음, 3번 비번 틀림
	public int login(UserDTO param) {
		String input_id = param.getUser_id();
		if(input_id.equals("")) {
			return Const.NO_ID;
		}
		UserDMI dbUser = mapper.selUser(param);
//		System.out.println("pw : " + dbUser.getUser_pw());
//		System.out.println("db salt1 : " + dbUser.getSalt());
//		
//		System.out.println("param id : " + param.getUser_id());
//		System.out.println("param pw : " + param.getUser_pw());
		if(dbUser.getUser_id().equals(input_id)\) {
			String salt = dbUser.getSalt();
//			System.out.println("db salt2 : " + dbUser.getSalt());
			String encrypt_input_pw = SecurityUtils.getEncrypt(param.getUser_pw(), salt);
//			System.out.println("input pw : " + encrypt_input_pw);
			if(encrypt_input_pw.equals(dbUser.getUser_pw())) {
				param.setUser_pw(null);
				return 1; // 성공
			} 
			return 3; // 비번 틀림
		
		} else {
			// 아이디 없음
			return 2;
		}
	}
	
	public int join(UserDTO param) {
		String pw = param.getUser_pw();
		String salt = SecurityUtils.generateSalt();
		String cryptPw = SecurityUtils.getEncrypt(pw, salt);
		
		param.setSalt(salt);
		param.setUser_pw(cryptPw);
		
		return mapper.insUser(param);
	}
}
