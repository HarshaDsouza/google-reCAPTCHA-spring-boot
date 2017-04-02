package com.captcha;

import java.util.Optional;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.captcha.model.ReCaptchaResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Component
public class CaptchaAuthFilter{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired private StoreManager storeManager;
	@Autowired private  SecRiskAnalyzer riskAnalyzer;
	private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";
	@Value("${captcha.secret}")
	private String secret;

	public void validateCaptcha(ServletRequest request, ServletResponse response){
		
		HttpServletRequest req = (HttpServletRequest)request; 
		HttpServletResponse resp = (HttpServletResponse)response;
		String sessionId = req.getSession().getId();
		
		
		if(!storeManager.isCaptchaEnabled() || !riskAnalyzer.ShouldProcessCapcha(req, resp, req.getSession().getId())){
			return;
		}
		
		
		String reCaptchaResponse = (String) request.getParameter("g-recaptcha-response");
		logger.debug(reCaptchaResponse);
		if(StringUtils.isEmpty(reCaptchaResponse)){
			riskAnalyzer.updateTracker(req, resp, false, sessionId);
			throw new BadCredentialsException("reCaptchaResponse is null. It seems like a haker attempt .. ");
		};
				
		ReCaptchaResponse captchaResponse = null;
		
		
		captchaResponse = getCaptchServerResponse(reCaptchaResponse)
				.orElseThrow(() -> new BadCredentialsException("Something is wrong while retrieving the google response. Check the logs."));				
		
		if(!captchaResponse.isSuccess()){
			riskAnalyzer.updateTracker(req, resp, false, sessionId);
			throw new BadCredentialsException("Invalid captcha Response. It seems like a haker attempt .. ");
		}else{
			riskAnalyzer.updateTracker(req, resp, true, sessionId);
		}
		
		logger.debug("Captcha is successfull! user is not a bot !!");
	}
	


	private Optional<ReCaptchaResponse> getCaptchServerResponse(String reCaptchaResponse) {
		ReCaptchaResponse captchaResponse =  null;
		try {

			Client client = Client.create();

			WebResource webResource = client
			   .resource(RECAPTCHA_URL);
						
			 MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
			  formData.add("secret", secret);
			  formData.add("response", reCaptchaResponse);	
			 
			
			ClientResponse resp = webResource
			   .post(ClientResponse.class, formData);

			if (resp.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				     + resp.getStatus());
			}								

			logger.debug("Output from Server .... \n");
			String output = resp.getEntity(String.class);
			logger.debug(output);
			ObjectMapper mapper = new ObjectMapper();
			captchaResponse= mapper.readValue(output, ReCaptchaResponse.class);

		  } catch (Exception e) {

			e.printStackTrace();

		  }
		return Optional.of(captchaResponse);
	}


}
