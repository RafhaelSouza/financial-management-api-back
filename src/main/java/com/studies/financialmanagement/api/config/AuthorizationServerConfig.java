package com.studies.financialmanagement.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    static final String CLIENT_FRONT = "frontend";
    static final String CLIENT_FRONT_SECRET = "$2a$10$uk4p6YPrnjxhXaA7wVXrMOshkWMm/K0JHNkJy0ZWmj4dLcUuf1Y4.";

    static final String CLIENT_MOBILE = "mobile";
    static final String CLIENT_MOBILE_SECRET = "$2a$10$BmUoJWYxh6w/JfPZDzXG0.yN971YOeBohmdavigL1PjkELzqFmFLK";

    static final String PASSWORD = "password";
    static final String AUTHORIZATION_CODE = "authorization_code";
    static final String REFRESH_TOKEN = "refresh_token";
    static final String IMPLICIT = "implicit";
    static final String SCOPE_READ = "read";
    static final String SCOPE_WRITE = "write";
    static final String TRUST = "trust";
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 3600;
    static final int REFRESH_TOKEN_VALIDITY_SECONDS = 3600 * 24;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("#front&nd");

        return accessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(CLIENT_FRONT)
                .secret(CLIENT_FRONT_SECRET)
                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
                .authorizedGrantTypes(PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS)
            .and()
                .withClient(CLIENT_MOBILE)
                .secret(CLIENT_MOBILE_SECRET)
                .scopes(SCOPE_READ)
                .authorizedGrantTypes(PASSWORD, REFRESH_TOKEN)
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(3600 * 24);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .reuseRefreshTokens(false)
                .authenticationManager(authenticationManager);
    }

}
