package com.project.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.http.*;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.project.service.SpaceService;
import com.project.vo.*;

@Controller
@RequestMapping(value = "/space")
public class SpaceController {

	@Autowired
	private SpaceService spaceService;
	
	@RequestMapping(value = "/register.do", method = RequestMethod.GET)
	public String register(HttpServletRequest request) {
		
		if (request.getSession().getAttribute("hlogin") == null) {
			return "redirect:/";
		}
		return "space/register";
	}
	
	@RequestMapping(value= "/register.do", method = RequestMethod.POST)
	public String register(SpacesVO vo, String[] spacePictureSrc, String[] thumbSrc) {

		if (thumbSrc != null && thumbSrc.length != 0) {
			vo.setThumb(thumbSrc[0]);
		}
		
		int result = spaceService.spaceReg(vo, spacePictureSrc);
		
		if (result > 0) {
			return "redirect:/space/myspace.do";
		} else {
			return null;
		}
	}
	

	@RequestMapping(value = "/uploadPicture.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> profileUpload(HttpServletRequest request, @RequestParam("picture") MultipartFile file) throws Exception {
		
		Map<String, String> pictures = new HashMap<String, String>();
		
		String path = request.getSession().getServletContext().getRealPath("/resources/upload");
		String fileName = file.getOriginalFilename();
		
		String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());

		UUID uuid = UUID.randomUUID();
		String newFileName = uuid.toString() + extension;

		File dir = new File(path);
		if (!dir.exists()) {	// 해당 디렉토리가 존재하지 않는 경우
			dir.mkdirs();				// 경로의 폴더가 없는 경우 상위 폴더에서부터 전부 생성
		}
		
		File target = new File(path, newFileName);
		
		file.transferTo(target);

    makeThumbnail(target.getAbsolutePath(), uuid.toString(), extension.substring(1), path, 200, 150);
    
    String original = "/upload/" + newFileName;
    String thumb = "/upload/THUMB_" + newFileName;
    
    pictures.put("original", original);
    pictures.put("thumb", thumb);
		
		return pictures;
	}

	private void makeThumbnail(String filePath, String fileName, String fileExt, String path, int width, int height) throws Exception {

		// 저장된 원본파일로부터 BufferedImage 객체를 생성합니다.
		BufferedImage srcImg = ImageIO.read(new File(filePath));

		// 썸네일의 너비와 높이 입니다.
		int dw = width, dh = height;

		// 원본 이미지의 너비와 높이 입니다.
		int ow = srcImg.getWidth();
		int oh = srcImg.getHeight();

		// 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
		int nw = ow; int nh = (ow * dh) / dw;

		// 계산된 높이가 원본보다 높다면 crop이 안되므로
		// 원본 높이를 기준으로 썸네일의 비율로 너비를 계산합니다.
		if(nh > oh) {
			nw = (oh * dw) / dh;
			nh = oh;
		}

		// 계산된 크기로 원본이미지를 가운데에서 crop 합니다.
		BufferedImage cropImg = Scalr.crop(srcImg, (ow-nw)/2, (oh-nh)/2, nw, nh);

		// crop된 이미지로 썸네일을 생성합니다.
		BufferedImage destImg = Scalr.resize(cropImg, dw, dh);

		// 썸네일을 저장합니다. 이미지 이름 앞에 "THUMB_" 를 붙여 표시했습니다.
		String thumbName = path + "\\THUMB_" + fileName + "." + fileExt;
		File thumbFile = new File(thumbName);
		
		ImageIO.write(destImg, fileExt.toUpperCase(), thumbFile);
	}
	
	@RequestMapping(value = "/myspace.do")
	public String myspace(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("hlogin") != null) {
			model.addAttribute("spacesVOs", spaceService.getSpaceList(((HostMembersVO) session.getAttribute("hlogin")).getmIdx()));
		} else if (session.getAttribute("login") != null) {
			if (((GeneralMembersVO) session.getAttribute("login")).getAuth() == 3) {
				model.addAttribute("spacesVOs", spaceService.getSpaceList(0));
			}
		}
		
		return "space/myspace";
	}
	
	@RequestMapping(value = "/getlocations.do")
	@ResponseBody
	public List<LocationsVO> getLocations() {
		return spaceService.getLocations();
	}

	@RequestMapping(value = "/details.do")
	public String details(Model model, SpacesVO vo) {
		model.addAttribute("spacesVO", spaceService.details(vo));
		model.addAttribute("spacePicturesVOs", spaceService.spacePictureList(vo));
		
		return "space/details";
	}

	@RequestMapping(value = "/delete.do")
	public String delete(Model model, HttpServletRequest request, SpacesVO vo) {
		
		if (request.getSession().getAttribute("hlogin") == null) {

			model.addAttribute("msg", "로그인이 필요합니다.");
			model.addAttribute("url", "/member/hlogin.do");
			
			return "alert";
		} else {
			
			vo = spaceService.details(vo);
			
			if (((HostMembersVO) request.getSession().getAttribute("hlogin")).getmIdx() == vo.getHostIdx()) {
				
				spaceService.delete(vo);
				
				return "redirect:/space/myspace.do";
			} else {

				model.addAttribute("msg", "권한이 없습니다.");
				model.addAttribute("url", "/space/details.do?idx=" + vo.getIdx());
				
				return "alert";
			}
		}
	}

	@RequestMapping(value = "/update.do")
	public String update(Model model, HttpServletRequest request, SpacesVO vo) {

		if (request.getSession().getAttribute("hlogin") == null) {

			model.addAttribute("msg", "로그인이 필요합니다.");
			model.addAttribute("url", "/member/hlogin.do");
			
			return "alert";
		} else {
			
			vo = spaceService.details(vo);
			
			if (((HostMembersVO) request.getSession().getAttribute("hlogin")).getmIdx() == vo.getHostIdx()) {
				
				return "redirect:/space/details.do?idx=" + vo.getIdx();
			} else {

				model.addAttribute("msg", "권한이 없습니다.");
				model.addAttribute("url", "/space/details.do?idx=" + vo.getIdx());
				
				return "alert";
			}
		}
	}

	@RequestMapping(value = "/getlikedstatus.do")
	@ResponseBody
	public int getLikedStatus(LikedSpacesVO vo) {
		return spaceService.getLikedStatus(vo);
	}

	@RequestMapping(value = "/likespace.do")
	@ResponseBody
	public int likeSpace(LikedSpacesVO vo) {
		System.out.println(vo.getmIdx() + " / " + vo.getSpaceIdx());
		return spaceService.likeSpace(vo);
	}

	@RequestMapping(value = "/unlikespace.do")
	@ResponseBody
	public int unlikeSpace(LikedSpacesVO vo) {
		return spaceService.unlikeSpace(vo);
	}

}
