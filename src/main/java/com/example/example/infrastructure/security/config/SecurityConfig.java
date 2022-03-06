package com.example.example.infrastructure.security.config;

import com.example.example.application.security.CustomUserDetailService;
import com.example.example.infrastructure.security.AuthTokenProvider;
import com.example.example.infrastructure.security.filter.JwtAuthenticationFilter;
import com.example.example.infrastructure.security.handler.FormLoginAuthenticationSuccessHandler;
import com.example.example.infrastructure.security.property.JwtAuthProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customUserDetailService;

    private final PasswordEncoder passwordEncoder;

    private final AuthTokenProvider authTokenProvider;

    private final JwtAuthProperty jwtAuthProperty;

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring().antMatchers("/favicon.ico", "/swagger-ui.html", "/apple-touch-icon.png", "/apple-touch-icon-precomposed.png")
                .and()
                .ignoring().mvcMatchers("/static/**", "/swagger-resources/**", "/webjars/**", "/swagger*/**", "/configuration/**", "/v2/**")
                .and()
                .ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/", "/login", "/signup", "/api/users", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
//                .defaultSuccessUrl("/success")
                .successHandler(new FormLoginAuthenticationSuccessHandler(authTokenProvider, jwtAuthProperty))
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(authTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
