package com.captcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application implements CommandLineRunner{
	
	public static final String IS_CAPTCHA_ENABLED_K = "IsCaptchaEnabled";
	@Value("${captcha.enabled}")
	private boolean captchaIsEnabled;
	
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
        
        System.out.println("-------------------- Application Started ----------------- ");
        
    }

	@Override
	public void run(String... args) throws Exception {
		
		
	}

}
