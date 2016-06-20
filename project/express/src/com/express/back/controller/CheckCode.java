package com.express.back.controller;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.patchca.color.ColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.font.FontFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = { "/back" })
public class CheckCode {

	@RequestMapping(value = { "/checkcode", "/" }, method = RequestMethod.GET)
	public void checkcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
		final Random random = new Random();
		cs.setColorFactory(new ColorFactory() {
			@Override
			public Color getColor(int x) {
				int[] c = new int[3];
				int i = random.nextInt(c.length);
				for (int fi = 0; fi < c.length; fi++) {
					if (fi == i) {
						c[fi] = random.nextInt(71);
					} else {
						c[fi] = random.nextInt(256);
					}
				}
				return new Color(c[0], c[1], c[2]);
			}
		});
		RandomWordFactory wf = new RandomWordFactory();
		wf.setCharacters("23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
		wf.setMaxLength(4);
		wf.setMinLength(4);
		cs.setWordFactory(wf);
		cs.setWidth(120);
		cs.setHeight(40);
		cs.setFontFactory(new FontFactory() {
			@Override
			public Font getFont(int arg0) {
				return new Font("Verdana", Font.PLAIN, 35);
			}
		});
		switch (random.nextInt(5)) {
		case 0:
			cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));//干扰线波纹 
			break;
		case 1:
			cs.setFilterFactory(new MarbleRippleFilterFactory());//大理石波纹
			break;
		case 2:
			cs.setFilterFactory(new DoubleRippleFilterFactory());//双波纹
			break;
		case 3:
			cs.setFilterFactory(new WobbleRippleFilterFactory());//摆波纹
			break;
		case 4:
			cs.setFilterFactory(new DiffuseRippleFilterFactory());//漫波纹
			break;
		}
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		 setResponseHeaders(response);
		 String token = EncoderHelper.getChallangeAndWriteImage(cs, "png", response.getOutputStream());
		 session.setAttribute("checkcode", token);
		 System.out.println("当前的SessionId=" + session.getId() + "，验证码=" + token);
	}
	
	  protected void setResponseHeaders(HttpServletResponse response) {
	        response.setContentType("image/png");
	        response.setHeader("Cache-Control", "no-cache, no-store");
	        response.setHeader("Pragma", "no-cache");
	        long time = System.currentTimeMillis();
	        response.setDateHeader("Last-Modified", time);
	        response.setDateHeader("Date", time);
	        response.setDateHeader("Expires", time);
	    }
}
