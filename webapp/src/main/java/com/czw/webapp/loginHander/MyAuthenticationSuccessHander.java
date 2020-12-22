package com.czw.webapp.loginHander;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHander implements AuthenticationSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MyAuthenticationSuccessHander.class);
    private DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LOG.info("进入了登陆成功操作");
        Object principal = authentication.getPrincipal();
        LOG.info(authentication.toString());
        LOG.info(authentication.getAuthorities().toString());
        LOG.info(String.valueOf(authentication.isAuthenticated()));
        LOG.info(authentication.getDetails().toString());
        if (principal instanceof UserDetails){
            LOG.info("用户名："+((UserDetails) principal).getUsername());
        }
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
    }
}
