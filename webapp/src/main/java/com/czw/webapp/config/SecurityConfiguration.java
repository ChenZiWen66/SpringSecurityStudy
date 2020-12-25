package com.czw.webapp.config;

import com.czw.webapp.filter.SmsCodeFilter;
import com.czw.webapp.filter.ValidateCodeFilter;
import com.czw.webapp.loginHander.MyAuthenticationFailureHander;
import com.czw.webapp.loginHander.MyAuthenticationSuccessHander;
import com.czw.webapp.services.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    MyAuthenticationSuccessHander myAuthenticationSuccessHander;
    @Autowired
    MyAuthenticationFailureHander myAuthenticationFailureHander;
    @Autowired
    ValidateCodeFilter validateCodeFilter;
    @Autowired
    SmsCodeFilter smsCodeFilter;
    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    SmsAuthenticationConfig smsAuthenticationConfig;
    @Autowired
    MyExpiredSessionStrategy myExpiredSessionStrategy;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                    .loginPage("/authentication/require")
                    .loginProcessingUrl("/loginaaa")
                    .successHandler(myAuthenticationSuccessHander)
                    .failureHandler(myAuthenticationFailureHander).and()
                .rememberMe()
                    .tokenRepository(persistentTokenRepository())//配置持久化仓库
                    .tokenValiditySeconds(30)//记住的时间，单位：秒
                    .userDetailsService(myUserDetailService).and()//处理自动登录逻辑
                .authorizeRequests()
                    .antMatchers("/authentication/require", "/login.html", "/code/image","/code/sms").permitAll()
                    .anyRequest().authenticated().and()
                .csrf().disable()
                .apply(smsAuthenticationConfig).and()
                .sessionManagement()
                    .invalidSessionUrl("/hello.html")
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .expiredSessionStrategy(myExpiredSessionStrategy);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }
}
