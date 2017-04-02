package com.captcha;

import javax.servlet.http.HttpServletRequest;

public final class WebUtil {

	public static String getIP(HttpServletRequest request) {


		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	public static String getUserAgent(HttpServletRequest request) {		
		 String userAgent = request.getHeader("user-agent");
		return userAgent;
	}
	
	public static String getBorwserEntityTag(HttpServletRequest request) {		
		String eTagFromBrowser = request.getHeader("If-None-Match");
		return eTagFromBrowser;
	}

}
