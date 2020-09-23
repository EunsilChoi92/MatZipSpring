package com.koreait.matzip.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;

@Service
public class RestService {

	@Autowired
	private RestMapper mapper;
	
	@Autowired
	private CommonMapper cMapper;
	
	public List<RestDMI> selRestList(RestPARAM param) {
//		List<RestDMI> list = mapper.selRestList(param);
//		if(list != null) {
//			Gson gson = new Gson();
//			return gson.toJson(list); 
//		}
		return mapper.selRestList(param);
	}
	
	public List<CodeVO> selCategoryList() {
		CodeVO param = new CodeVO();
		param.setI_m(1);
		return cMapper.selCodeList(param);
	}
	
	
	public int insRest(RestPARAM param) {
		return mapper.insRest(param);
	}
	
	public RestDMI selRest(RestPARAM param) {
		return mapper.selRest(param);
	}
	
	@Transactional
	public void delRestTran(RestPARAM param) {
		mapper.delRestRecMenu(param);
		mapper.delRestMenu(param);
		mapper.delRest(param);
	}

	public int delRestRecMenu(RestPARAM param) {
		return mapper.delRestRecMenu(param);
	}
	
	public int delRestMenu(RestPARAM param) {
		return mapper.delRestMenu(param);
	}
	
	public int delRest(RestPARAM param) {
		return mapper.delRest(param);
	}
	
	public int insRecMenus(MultipartHttpServletRequest mReq) {
		
		int i_rest = Integer.parseInt(mReq.getParameter("i_rest"));
		List<MultipartFile> fileList = mReq.getFiles("menu_pic");
		String[] menuNmArr = mReq.getParameterValues("menu_nm");
		String[] menuPriceArr = mReq.getParameterValues("menu_price");
		
		// 저장 위치
		String path = mReq.getServletContext().getRealPath(
				"/resources/img/rest/" + i_rest + "/rec_menu/");

		List<RestRecMenuVO> list = new ArrayList();
		
		
		for(int i=0; i<menuNmArr.length; i++) {
			RestRecMenuVO vo = new RestRecMenuVO();
			
			list.add(vo);
			
			String menu_nm = menuNmArr[i];
			int menu_price = CommonUtils.parseStringToInt(menuPriceArr[i]);
			vo.setMenu_nm(menu_nm);
			vo.setMenu_price(menu_price);
			vo.setI_rest(i_rest);
			
			// 파일 각 저장
			MultipartFile mf = fileList.get(i);
			
			if(mf.isEmpty()) {
				continue;
			}
			
			String originFileNm = mf.getOriginalFilename();
			String ext = FileUtils.getExt(originFileNm);
			String saveFileNm = UUID.randomUUID() + ext;
			
			
			try {
				mf.transferTo(new File(path + saveFileNm));
				vo.setMenu_pic(saveFileNm);
			} catch(Exception e) {
				e.printStackTrace();
			} 
		}
		
		for(RestRecMenuVO vo : list) {
			mapper.insRestRecMenu(vo);
		}
		
		return i_rest;
		
	}
	
	
	public List<RestRecMenuVO> selRestRecMenus(RestPARAM param) {
		return mapper.selRestRecMenus(param);
	}
	
	public int delRecMenu(RestPARAM param, String realPath) {
		// 파일 삭제
		List<RestRecMenuVO> list = mapper.selRestRecMenus(param);
		
		if(list.size() == 1) {
			RestRecMenuVO item = list.get(0);
			
			if(item.getMenu_pic() != null && !item.getMenu_pic().contentEquals("")) { // 이미지 있는지 확인 후 삭제
				File file = new File(realPath + item.getMenu_pic());
				if(file.exists()) {
					if(file.delete()) {
						return mapper.delRestRecMenu(param);
					} else {
						return 0;
					}
				}	
			}
		}
		return mapper.delRestRecMenu(param);
	}
}
