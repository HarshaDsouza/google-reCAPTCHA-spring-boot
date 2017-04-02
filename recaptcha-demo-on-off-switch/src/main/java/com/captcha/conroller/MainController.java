package com.captcha.conroller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.captcha.Application;
import com.captcha.StoreManager;

@Controller

public class MainController {
	
	@Autowired private StoreManager storeManager;
	
	@RequestMapping("/login")
	public ModelAndView login(){
		ModelAndView view = new ModelAndView("login");
				
		view.addObject("captchaIsEnabled", storeManager.isCaptchaEnabled());
		return view;
		
		
	}
	
	
	@RequestMapping("/switchFlag")
	public void switchFlag(HttpServletResponse response) throws IOException{
		boolean oldVal = (boolean) storeManager.getValue(Application.IS_CAPTCHA_ENABLED_K);
		storeManager.setValue(Application.IS_CAPTCHA_ENABLED_K, !oldVal);
		response.getWriter().println("Flag switched successfully. New value: "+ storeManager.isCaptchaEnabled() );
		response.getWriter().println("\nNote: This url is public and not protected, since it's only for demo purposes. In production this link would be protected and only available under DMZ network");
	}

}
