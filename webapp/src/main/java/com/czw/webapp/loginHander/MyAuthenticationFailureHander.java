package com.czw.webapp.loginHander;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHander implements AuthenticationFailureHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MyAuthenticationFailureHander.class);
    private DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private HttpSessionRequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        LOG.info("登陆失败了");
        if (e instanceof BadCredentialsException){
            LOG.info("aaaaaaaaaaaaaaaaa");
        }
        if (e instanceof LockedException){
            LOG.info("bbbbbbbbbbbbbbbbb");
        }
        LOG.info(e.toString());
//        SavedRequest savedRequest = requestCache.getRequest(request,response);
        redirectStrategy.sendRedirect(request,response,"/hello");
    }
}