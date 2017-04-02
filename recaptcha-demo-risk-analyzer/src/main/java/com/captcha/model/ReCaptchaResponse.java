package com.captcha.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ReCaptchaResponse {
	private boolean success;
	 @JsonProperty("challenge_ts")
	private String challengeTs;
	private String hostname;

	
	
	/**
	 * @return the challengeTs
	 */
	public String getChallengeTs() {
		return challengeTs;
	}
	/**
	 * @param challengeTs the challengeTs to set
	 */
	public void setChallengeTs(String challengeTs) {
		this.challengeTs = challengeTs;
	}
	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}
	/**
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
