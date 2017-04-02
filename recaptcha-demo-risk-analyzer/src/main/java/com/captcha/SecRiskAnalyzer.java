package com.captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class SecRiskAnalyzer {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String PARAM_E_TAG = "ETag";
	public static final String PARAM_CACHE_CONTROL = "Cache-Control";
	private static final String PARAM_USER_AGENT = "USER_AGENT";
	private static final String PARAM_IP = "IP";
	@Autowired
	private StoreManager storeManager;
	
	
	public boolean shouldAskForChallange(HttpServletRequest req, HttpServletResponse resp){
		boolean res = true;
		if(storeManager.isCaptchaEnabled()){
			
			String sessionId = req.getSession(true).getId();
			UserTracker tracker = null;
			if(StringUtils.isNoneBlank(sessionId) && storeManager.containsKey(sessionId)){
				// check if trusted 
				tracker =(UserTracker) storeManager.getValue(sessionId);
				res = IsTrakerValidForRequest(req, tracker)
						;				
			}else{
				tracker = startTracker(req, resp, sessionId);				
			}
			
			tracker.setValidateCaptcha(res);
		}
		return res;
	}



	/**
	 *  Analyze the data to determine the risk level. 
	 * @param req
	 * @param tracker
	 * @return
	 */
	private boolean IsTrakerValidForRequest(HttpServletRequest req, UserTracker tracker) {
		logger.debug("IsTrakerValidForRequest");
		logger.debug("User passed Captcha Chalange before:{} ",tracker.isPassCaptcha());
		
		logger.debug("Matching IP[tracker:{} , request:{}]: {}",tracker.getInfo(PARAM_IP),WebUtil.getIP(req),(tracker.contains(PARAM_IP) && tracker.getInfo(PARAM_IP).equals(WebUtil.getIP(req))));	
		
		logger.debug("Matching User Agent [tracker:{} , request:{}]: {}",tracker.getInfo(PARAM_USER_AGENT),WebUtil.getIP(req),(tracker.contains(PARAM_USER_AGENT) && tracker.getInfo(PARAM_USER_AGENT).equals(WebUtil.getUserAgent(req))));		
		return !tracker.isPassCaptcha() && 
				(tracker.contains(PARAM_IP) && tracker.getInfo(PARAM_IP).equals(WebUtil.getIP(req))) &&
				(tracker.contains(PARAM_USER_AGENT) && tracker.getInfo(PARAM_USER_AGENT).equals(WebUtil.getUserAgent(req)));
	}
	
	
	
	
	public String getTrakerId(HttpServletResponse resp){
		return resp.getHeader(SecRiskAnalyzer.PARAM_E_TAG);
	}
	
	public boolean ShouldProcessCapcha(HttpServletRequest req, HttpServletResponse resp, String id){
		boolean res = true;
		if(storeManager.isCaptchaEnabled()){
			
			if(StringUtils.isNoneBlank(id) && storeManager.containsKey(id)){				
				UserTracker tracker =(UserTracker) storeManager.getValue(id);
				res = tracker.isValidateCaptcha();				
			}else{
				res = false;
			}									
		}
		return res;
	}
	
	public void updateTracker(HttpServletRequest req, HttpServletResponse resp, boolean capchaRepsone,String  id){
		UserTracker tracker = (UserTracker) storeManager.getValue(id);
		tracker.updateAttempt(capchaRepsone);	
	}
	
	public UserTracker startTracker(HttpServletRequest req, HttpServletResponse resp, String id){
		UserTracker tracker =  new UserTracker(id);
		tracker.addInfo(SecRiskAnalyzer.PARAM_IP, WebUtil.getIP(req));
		tracker.addInfo(SecRiskAnalyzer.PARAM_USER_AGENT, WebUtil.getUserAgent(req));
		//store
		storeManager.setValue(tracker.getId(), tracker);
		//update response
		
		return tracker;
	}


}
