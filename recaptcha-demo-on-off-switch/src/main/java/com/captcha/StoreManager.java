package com.captcha;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This implementation is only for demo purposes. In production a more reliable solutions such Redis, MemedChach ...  should be used
 * @author salrosan
 *
 */
@Component
public class StoreManager {
	


	@Value("${captcha.enabled}")
	private boolean captchaIsEnabled;
 

	private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
	
	
	@PostConstruct
	public void init(){
		map.put(Application.IS_CAPTCHA_ENABLED_K, captchaIsEnabled);
	}
	
		
	public boolean isCaptchaEnabled(){
		return (boolean) map.get(Application.IS_CAPTCHA_ENABLED_K);
	}
	
	public void setValue(String key, Object value){
		map.put(key, value);

	}
	
	
	public Object getValue(String Key){
		return map.get(Key);
	}
	
}
