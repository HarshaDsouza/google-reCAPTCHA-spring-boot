package com.captcha;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.captcha.auth.filter.login.LoginProcessingFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/login";

	private static final String DEFAULT_FAILURE_URL = FORM_BASED_LOGIN_ENTRY_POINT + "?error";

	@Autowired
	LoginProcessingFilter authenticationFilter;

	@Autowired
	AuthenticationProvider loginAuthenticationProvider;
	
	


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	



	@Bean
	public ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
		registration.addUrlMappings("/console/*");
		return registration;
	}

	
	@Bean
	@Autowired
	protected LoginProcessingFilter LoginProcessingFilter(CaptchaAuthFilter captchaAuthFilter) throws Exception {
		LoginProcessingFilter authenticationFilter = new LoginProcessingFilter(DEFAULT_FAILURE_URL,
				captchaAuthFilter);
		authenticationFilter.setAuthenticationManager(authenticationManager());
		return authenticationFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/", "/home","/switchFlag").permitAll().antMatchers("/css/**", "/js/**","/favicon.ico").permitAll()
				.anyRequest().authenticated().and().formLogin().loginPage(FORM_BASED_LOGIN_ENTRY_POINT).permitAll()
				.and().logout().permitAll().and().addFilter(authenticationFilter)
				.csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(loginAuthenticationProvider);
	}
}
