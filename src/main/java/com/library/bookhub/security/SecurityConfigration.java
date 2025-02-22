package com.library.bookhub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.library.bookhub.security.oauth.Oauth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfigration implements WebMvcConfigurer {
	
	@Autowired
	private SecurityUserService service;
	
	@Autowired
	private Oauth2UserService oAuth2UserService;
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
			// 사이트 위변조 방지 비활성화
			.csrf(CsrfConfigurer::disable)
			// X-Frame-Options 비활성화(h2 DB 사용하기 위함)
			.headers(header -> header
					.frameOptions(FrameOptionsConfig::disable))
			// 로그인 설정
			.formLogin(config -> config
					.loginPage("/login")
					.defaultSuccessUrl("/", true)
					.failureUrl("/login?success=401")
	                .usernameParameter("username")
	                .passwordParameter("password")
                    .permitAll())
			// 로그아웃 설정
            .logout(config -> config
                    .logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/login?success=200")
                    .deleteCookies("JSESSIONID"))
            // 자동 로그인 설정
            .rememberMe(remember -> remember
                    .rememberMeParameter("remember")
                    .alwaysRemember(false)
                    .tokenValiditySeconds(60*60*24*7)
                    .key("rememberMe")
                    .userDetailsService(service))
            // 소셜 로그인 설정
            .oauth2Login(oauth -> oauth
            		.loginPage("/login")
            		.userInfoEndpoint(userInfo -> userInfo
            				.userService(oAuth2UserService))
            		.clientRegistrationRepository(clientRegistrationRepository())
            		.defaultSuccessUrl("/",true)
    	            .failureUrl("/login?success=403")
    	            .permitAll())
            // 인가 권한 설정
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            		.requestMatchers("/**").permitAll()
            		.requestMatchers("/carousel").permitAll()
            		.requestMatchers("/home","/").permitAll()
            		.requestMatchers("/header","/footer").permitAll()
            		.requestMatchers("/login").permitAll()
            		.requestMatchers("/point/calendarPoint").authenticated()
            		.requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers(PathRequest.toH2Console()).authenticated()
            	    .requestMatchers("/admin").hasAuthority("ADMIN") // "/admin" 경로에 대한 권한 설정
            	    .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**").permitAll());

		// 사용자 인증처리 컴포넌트 등록
		http.userDetailsService(service);
		
		return http.build();
			
	}
	
	
	
	// 비밀번호 암호화
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 소셜 로그인 등록
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
	    return new InMemoryClientRegistrationRepository(
	        kakaoClientRegistration(),
	        googleClientRegistration(),
	        naverClientRegistration()
	    );
	}
	
	// 카카오 소셜 로그인
	private ClientRegistration kakaoClientRegistration() {
	    return ClientRegistration.withRegistrationId("kakao")
	            .clientId("daa9133dd9b91f5965b4bdb82517dc70")
	            .clientSecret(null)
	            .redirectUri("http://localhost/login/oauth2/code/kakao")
	            .authorizationUri("https://kauth.kakao.com/oauth/authorize")
	            .tokenUri("https://kauth.kakao.com/oauth/token")
	            .userInfoUri("https://kapi.kakao.com/v2/user/me")
	            .userNameAttributeName("id")
	            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	            .clientName("Kakao")
	            .build();
	}
	
	// 구글 소셜 로그인
	private ClientRegistration googleClientRegistration() {
	    return ClientRegistration.withRegistrationId("google")
	            .clientId("683257437244-25rfuj1rvvk8tbl5tt1qa2n43in7g65u.apps.googleusercontent.com")
	            .clientSecret("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")
	            .redirectUri("http://localhost/login/oauth2/code/google")
	            .scope("profile", "email")
	            .authorizationUri("https://accounts.google.com/o/oauth2/auth")
	            .tokenUri("https://www.googleapis.com/oauth2/v4/token")
	            .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
	            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	            .userNameAttributeName(IdTokenClaimNames.SUB)
	            .clientName("Google")
	            .build();
	}
	
	// 네이버 소셜 로그인
	private ClientRegistration naverClientRegistration() {
	    return ClientRegistration.withRegistrationId("naver")
	            .clientId("BIr6OZUn4vkMLYBrGi7P")
	            .clientSecret("ko5_hFFCGV")
	            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
	            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	            .redirectUri("http://localhost/login/oauth2/code/naver")
	            .scope("profile")
	            .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
	            .tokenUri("https://nid.naver.com/oauth2.0/token")
	            .userInfoUri("https://openapi.naver.com/v1/nid/me")
	            .userNameAttributeName("response")
	            .clientName("Naver")
	            .build();
	}
	
	
	public void configure(WebSecurity web, AuthenticationManagerBuilder auth) throws Exception {
	    web.httpFirewall(defaultHttpFirewall());
	    
	    auth.userDetailsService(service)
        // 비밀번호를 저장할 때 인코딩 설정 (필요에 따라 변경 가능)
        .passwordEncoder(passwordEncoder());
	}
	
	// 더블 슬래시 허용
	@Bean
	public HttpFirewall defaultHttpFirewall() {
	    return new DefaultHttpFirewall();
	    
	}

	@Bean
    public GrantedAuthority socialUserRole() {
        return new SimpleGrantedAuthority("ROLE_USER");
    }
	
	
}

