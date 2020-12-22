package com.czw.webapp.config;

import com.czw.webapp.loginHander.MyAuthenticationFailureHander;
import com.czw.webapp.loginHander.MyAuthenticationSuccessHander;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    MyAuthenticationSuccessHander myAuthenticationSuccessHander;
    @Autowired
    MyAuthenticationFailureHander myAuthenticationFailureHander;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/loginaaa")
                .successHandler(myAuthenticationSuccessHander)
                .failureHandler(myAuthenticationFailureHander)
                .and()
                .authorizeRequests()
                        .antMatchers("/authentication/require").permitAll()
                        .antMatchers("/login.html").permitAll()
                        .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
