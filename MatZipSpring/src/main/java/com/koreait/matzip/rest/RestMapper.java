package com.koreait.matzip.rest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestFile;
import com.koreait.matzip.rest.model.RestMenuVO;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;

@Mapper
public interface RestMapper {
	public int selRestChkUser(int param);
	public List<RestDMI> selRestList(RestPARAM param);
	public RestDMI selRest(RestPARAM param);
	public List<RestRecMenuVO> selRestRecMenus(RestPARAM param);
	public List<RestMenuVO> selRestMenus(RestPARAM param);
	
	public int insRest(RestPARAM param);
	public int insRestRecMenu(RestRecMenuVO param);
	public int insRestMenus(RestMenuVO param);
	
	public int delRestRecMenu(RestPARAM param);
	public int delRestMenu(RestPARAM param);
	public int delRest(RestPARAM param);

	
}
