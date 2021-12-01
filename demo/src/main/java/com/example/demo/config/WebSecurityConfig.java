package com.example.demo.config;


import java.util.Arrays;

import com.example.demo.CORSFilter;
import com.example.demo.jwt.JwtAuthenticationFilter;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
                System.out.println("FFGGGG");
        auth.userDetailsService(userService) 
            .passwordEncoder(passwordEncoder()); 
    }

    @Bean
    CORSFilter corsFilter() {
        CORSFilter filter = new CORSFilter();
        return filter;
    }

    // @Bean
	// CorsConfigurationSource corsConfigurationSource() {
    //     System.out.println("OKKKKKKK");
	// 	CorsConfiguration configuration = new CorsConfiguration();
	// 	configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
	// 	configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
	// 	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	// 	source.registerCorsConfiguration("/**", configuration);
	// 	return source;
	// }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(corsFilter(), SessionManagementFilter.class)
            .csrf()
            .disable()
            // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // .and()
            .authorizeRequests()
            // .antMatchers("/admin").hasRole("ADMIN")
            // .antMatchers("/news/*").hasRole("USER")
            .antMatchers("/user/update_avatar").hasAnyRole("ADMIN","USER")
            // .antMatchers("/gameplay/xo/*").hasAnyRole("ADMIN","USER")
            // .antMatchers("/gameplay").hasAnyRole("ADMIN","USER")
            .anyRequest().permitAll()
            .and()
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
