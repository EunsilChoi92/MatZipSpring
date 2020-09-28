package com.koreait.matzip.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestFile;
import com.koreait.matzip.rest.model.RestMenuVO;
import com.koreait.matzip.rest.model.RestPARAM;
import com.koreait.matzip.rest.model.RestRecMenuVO;

@Service
public class RestService {

	@Autowired
	private RestMapper mapper;
	
	@Autowired
	private CommonMapper cMapper;
	
	public int selRestChkUser(int i_rest) {
		return mapper.selRestChkUser(i_rest);
	}
	
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
	
	public void updAddHits(RestPARAM param, HttpServletRequest req) {
		String myIp = req.getRemoteAddr();
		ServletContext ctx = req.getServletContext();
		
		System.out.println("오니??");
		int i_user = SecurityUtils.getLoginUserPk(req);
		System.out.println("꺄아아 : " + i_user);
		
		String currentRestReadeIp = (String)ctx.getAttribute(Const.CURRENT_REST_READ_IP + param.getI_rest());
		if(currentRestReadeIp == null || !currentRestReadeIp.equals(myIp)) {
			param.setI_user(i_user); // 내가 쓴 글이면 조회수 안 올라가게 쿼리문으로 막음
			// 조회수 올림 처리
			mapper.updAddHits(param);
			ctx.setAttribute(Const.CURRENT_REST_READ_IP + param.getI_rest(), myIp);
		}
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
	
	public int delRest(RestPARAM param) {
		return mapper.delRest(param);
	}
	
	public int insRecMenus(MultipartHttpServletRequest mReq) {
		int i_rest = Integer.parseInt(mReq.getParameter("i_rest"));
		
		List<MultipartFile> fileList = mReq.getFiles("menu_pic");
		String[] menuNmArr = mReq.getParameterValues("menu_nm");
		String[] menuPriceArr = mReq.getParameterValues("menu_price");
		
		// 저장 위치
//		String path = mReq.getServletContext().getRealPath(
//				"/resources/img/rest/" + i_rest + "/rec_menu/");
		
		String path = Const.realPath + "/resources/img/rest/" + i_rest + "/rec_menu/"; 

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

			System.out.println(path);
			System.out.println(saveFileNm);
			
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
	
	public int insRestMenus(RestFile param, int i_user) {
		String path = Const.realPath + "/resources/img/rest/" + param.getI_rest() + "/menu/";
		
		List<RestMenuVO> list = new ArrayList();
		
		for(MultipartFile file : param.getMenu_pic()) {
			RestMenuVO vo = new RestMenuVO();
			list.add(vo);
			vo.setI_rest(param.getI_rest());
			vo.setMenu_pic(FileUtils.saveFile(path, file));
		}
		
		for(RestMenuVO vo : list) {
			mapper.insRestMenus(vo);			
		}
		
		return Const.SUCCESS;
		
	}
	
	
	public List<RestRecMenuVO> selRestRecMenus(RestPARAM param) {
		return mapper.selRestRecMenus(param);
	}
	
	public List<RestMenuVO> selRestMenus(RestPARAM param) {
		return mapper.selRestMenus(param);
	}
	
	public int delRestRecMenu(RestPARAM param, String realPath) {
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
	
	public int delRestMenu(RestPARAM param) {
		// 파일 삭제
		if(param.getMenu_pic() != null && !"".equals(param.getMenu_pic())) {
			String path = Const.realPath + "/resources/img/rest/" + param.getI_rest() + "/menu/";
			System.out.println("보여줘잉 : " + path + param.getMenu_pic());
			if(FileUtils.delFile(path + param.getMenu_pic())) {
				return mapper.delRestMenu(param);
			} else {
				return Const.FAIL;
			}			
		}
		return mapper.delRestMenu(param);
	}
}
