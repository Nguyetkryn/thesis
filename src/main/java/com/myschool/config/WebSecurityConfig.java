package com.myschool.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	            .authorizeHttpRequests(authorize -> authorize
	                    .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("bangiamhieu")
	                    .requestMatchers(new AntPathRequestMatcher("/teacher/**")).hasRole("giaovien")
	                    .requestMatchers(new AntPathRequestMatcher("/hteacher/**")).hasRole("giaoviencn")
	                    .anyRequest().permitAll())
	            .formLogin(form -> form
	            	    .loginPage("/login")
	            	    .loginProcessingUrl("/login")
	            	    .successHandler((request, response, authentication) -> {
	            	        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
	            	        if (roles.contains("ROLE_bangiamhieu")) {
	            	            response.sendRedirect("/admin/home");
	            	        }
	            	        else if (roles.contains("ROLE_giaovien")) {
	            	        	response.sendRedirect("/teacher/home");
	            	        }
	            	        else if (roles.contains("ROLE_giaoviencn")) {
	            	        	response.sendRedirect("/hteacher/home");
	            	        }
	            	        else {response.sendRedirect("/home");}
	            	    })
	            	    .permitAll())
	            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
	                    .logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("JSESSIONID");
	    return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}
