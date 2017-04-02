package com.captcha.conroller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.captcha.SecRiskAnalyzer;
import com.captcha.StoreManager;

@Controller

public class MainController {

	private static final String VIEW_ATTR_CAPTCHA_IS_ENABLED = "captchaIsEnabled";
	@Autowired
	private StoreManager storeManager;
	@Autowired private  SecRiskAnalyzer riskAnalyzer;

	@GetMapping(value="/login")
	public ModelAndView login(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("MainController.login()");
		ModelAndView view = new ModelAndView("login");
		
		boolean showCaptcha = false;
		if(storeManager.isCaptchaEnabled() && riskAnalyzer.shouldAskForChallange(req, resp)){
			showCaptcha = true;
			//view.addObject("et", riskAnalyzer.getTrakerId(resp));			
		}
		
		view.addObject(VIEW_ATTR_CAPTCHA_IS_ENABLED, showCaptcha);
		return view;
	}

	@RequestMapping("/switchFlag")
	public void switchFlag(HttpServletResponse response) throws IOException {		
		response.getWriter().println("Flag has been successfully switched. New value: " + storeManager.SwitchisCaptchaEnabled());
		response.getWriter().println("\nNote: This url is public and not protected, since it's only for demo purposes. In production this link would be protected and only available under DMZ network");
		
	}

}
