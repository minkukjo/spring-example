package com.example.example.infrastructure.security.handler;

import com.example.example.infrastructure.security.AuthToken;
import com.example.example.infrastructure.security.AuthTokenProvider;
import com.example.example.infrastructure.security.property.JwtAuthProperty;
import com.example.example.utils.CookieUtil;
import java.io.IOException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AuthTokenProvider tokenProvider;
    private final JwtAuthProperty jwtAuthProperty;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        final User principal = (User) authentication.getPrincipal();

        AuthToken accessToken = tokenProvider.createToken(
                principal.getUsername(),
                "ROLE_USER",
                new Date(new Date().getTime() + 1800000)
        );

        CookieUtil.deleteCookie(request, response, "access_token");
        CookieUtil.addCookie(response, "access_token", accessToken.getToken(), 30000);

        response.sendRedirect(jwtAuthProperty.getRedirectUri());
    }
}
