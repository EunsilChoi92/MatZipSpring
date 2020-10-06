package com.koreait.matzip.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserPARAM;

@Mapper
public interface UserMapper {
	public int insUser(UserPARAM param);
	public int insFavorite(UserPARAM param);
	
	public UserDMI selUser(UserPARAM param);
	public List<UserDMI> selFavoriteList(UserPARAM param);
	
	public int delFavorite(UserPARAM param);
	
	
}
