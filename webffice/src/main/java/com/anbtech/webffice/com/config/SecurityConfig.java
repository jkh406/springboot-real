package com.anbtech.webffice.com.config;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.anbtech.webffice.com.jwt.JwtAccessDeniedHandler;
import com.anbtech.webffice.com.jwt.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;    
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public MyAuthorizationManager myAuthorizationManager() {
        return new MyAuthorizationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests() //요청 URL에 따라 접근 권한을 설정
        	.requestMatchers("/api/v1/login").permitAll()
        	.requestMatchers("/api/v1/logout").permitAll()
        	.requestMatchers("/api/v1/token/getAccessToken").permitAll()
        	.requestMatchers("/404").permitAll()
        	.requestMatchers("/error").permitAll()
    		.anyRequest().access(myAuthorizationManager());
        http.formLogin()
            .loginPage("/auth/login")
    		.defaultSuccessUrl("/")
    		.usernameParameter("user_Id")
            .passwordParameter("user_Pw");
        http.cors().configurationSource(corsConfigurationSource());
		http.csrf().disable(); //세션을 사용하지 않고 JWT 토큰을 활용하여 진행, csrf토큰검사를 비활성화
		http.httpBasic().disable();
        http.exceptionHandling();
		http.sessionManagement()
		 	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // jwt token으로 인증하므로 세션은 stateless 하도록 처리.
		http.exceptionHandling()  //예외처리
		    .accessDeniedHandler(jwtAccessDeniedHandler)
		    .authenticationEntryPoint(jwtAuthenticationEntryPoint); //사용자 인증방법
//		http.apply(new JwtSecurityConfig(jwttokenProvider));
		
      return http.build();
	}
    
    
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return (CorsConfigurationSource) source;
	}
}
