package com.captcha;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserTracker {	
	private final String id;
	
	private Map<String,Object> info = Collections.synchronizedMap(new HashMap<>());
	
	
	private boolean passCaptcha;
	
	private boolean validateCaptcha;
	
	public UserTracker(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void addInfo(String key, Object value){
		info.put(key, value);
	}
	
	public Object getInfo(String key){
		return info.get(key);
	}
	
	public Object removeInfo(String key){
		return info.remove(key);
	}
	
	public boolean contains(String key){
		return info.containsKey(key);
	}

	public boolean isPassCaptcha() {
		return passCaptcha;
	}

	public void setPassCaptcha(boolean passCaptcha) {
		this.passCaptcha = passCaptcha;
	}
	

	public void updateAttempt(boolean capchaRepsone) {
		this.setPassCaptcha(capchaRepsone);
		this.addInfo("lastAttempt", new Date());		
		
		info.merge("badAttempsCount", 1, (oldval , newVal) -> {return ((int)oldval+(int)newVal);});
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserTracker [id=" + id + ", info=" + info + ", passCaptcha=" + passCaptcha + "]";
	}

	public boolean isValidateCaptcha() {
		return validateCaptcha;
	}

	public void setValidateCaptcha(boolean validateCaptcha) {
		this.validateCaptcha = validateCaptcha;
	}
	
	


	
}
