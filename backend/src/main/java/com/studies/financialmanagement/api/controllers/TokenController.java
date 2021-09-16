package com.studies.financialmanagement.api.controllers;

import com.studies.financialmanagement.api.config.property.ApiProperty;
import com.studies.financialmanagement.api.token.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private ApiProperty apiProperty;

    @DeleteMapping("/revoke")
    public void revoke(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(TokenUtils.REFRESH_TOKEN_COOKIE, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(apiProperty.getSecurity().isEnableHttps());
        cookie.setPath(request.getContextPath() + TokenUtils.OAUTH_TOKEN_URL);
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }

}
