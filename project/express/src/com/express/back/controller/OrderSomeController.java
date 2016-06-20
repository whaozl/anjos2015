package com.express.back.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.express.back.dto.BackUserLoginData;
import com.express.back.service.OrderSomeService;

@Controller
@RequestMapping(value={"/back"}, produces = "application/json;charset=UTF-8")
public class OrderSomeController {
	
	private OrderSomeService service=new OrderSomeService();
	
	@RequestMapping(value={"/order_some"}, method=RequestMethod.GET)
	public String Index(){
		return "back/order_some";
	}
	
	@RequestMapping(value={"/InputModelxls"}, method=RequestMethod.GET)
	public ResponseEntity<byte[]> InputModel_xls(HttpServletRequest request) throws IOException{
		String path= request.getSession().getServletContext().getRealPath("");//获得项目根目录
		File file=new File(path+"/WEB-INF/down/InputModel.xls");
		HttpHeaders headers = new HttpHeaders();
		String fileName=new String("InputModel.xls".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题 
	    headers.setContentDispositionFormData("attachment", fileName);
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value={"/InputModelcsv"}, method=RequestMethod.GET)
	public ResponseEntity<byte[]> InputModel_csv(HttpServletRequest request) throws IOException{
		String path= request.getSession().getServletContext().getRealPath("");//获得项目根目录
		File file=new File(path+"/WEB-INF/down/InputModel.csv");
		HttpHeaders headers = new HttpHeaders();
		String fileName=new String("InputModel.csv".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题 
	    headers.setContentDispositionFormData("attachment", fileName);
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/UploadModel", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String UploadModel(MultipartFile file1,HttpServletRequest request) throws IOException {
		HttpSession session=request.getSession();
		BackUserLoginData buld=(BackUserLoginData) session.getAttribute("buld");
		if(file1==null || file1.isEmpty()){
			return "上传失败";
		}
		System.out.println(file1.getContentType()+","+file1.getName()+","+file1.getOriginalFilename());
		String realpath = session.getServletContext().getRealPath("/resources/upload/");
		System.out.println(realpath);
		String filePath=realpath+"//"+file1.getOriginalFilename();
		File file= new File(filePath);
		FileUtils.copyInputStreamToFile(file1.getInputStream(), file);
		String result=this.service.UploadModel(buld, file);
		System.out.println(result);
		return result;
	}

}
