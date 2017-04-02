package com.captcha.auth.filter.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.captcha.CaptchaAuthFilter;


public class LoginProcessingFilter extends UsernamePasswordAuthenticationFilter {
	private static Logger logger = LoggerFactory.getLogger(LoginProcessingFilter.class);

	CaptchaAuthFilter captchaAuthFilter;

	public LoginProcessingFilter(String defaultFailureUrl, CaptchaAuthFilter captchaAuthFilter) {
		((SimpleUrlAuthenticationFailureHandler) getFailureHandler()).setDefaultFailureUrl(defaultFailureUrl);
		this.captchaAuthFilter = captchaAuthFilter;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		logger.debug("Validate Captcha in the request");
		captchaAuthFilter.validateCaptcha(request, response);
		;

		return super.attemptAuthentication(request, response);
	}

}
