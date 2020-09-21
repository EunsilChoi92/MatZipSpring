package com.koreait.matzip.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;

@Service
public class RestService {
	 
	@Autowired
	private RestMapper mapper;
	
	String selRestList(RestPARAM param) {
		List<RestDMI> list = mapper.selRestList(param);
		if(list != null) {
			Gson gson = new Gson();
			return gson.toJson(list); 
		}
		return "";
	}
	
	public int insRest(RestPARAM param) {
		return mapper.insRest(param);
	}
}
