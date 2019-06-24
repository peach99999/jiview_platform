package com.smaller.jiview.moduler.system.config.security;

import com.smaller.jiview.core.config.security.JwtAuthenticationEntryPoint;
import com.smaller.jiview.core.config.security.WebSecurityConfig;
import com.smaller.jiview.core.constant.UrlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author xigf 2019/05/23
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminWebSecurityConfig extends WebSecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // swagger uri
                .antMatchers(
                        HttpMethod.GET,
                        "/swagger*/**",
                        "/webjars/**",
                        "/v2/api-docs"
                ).permitAll()
                // common uri
                .antMatchers(
                        HttpMethod.GET,
                        UrlConstants.DRUID_STAT_URIS,
                        "/favicon.ico",
                        "api/error",
                        "/error/**",
                        "/web/**"
                ).permitAll()
                // custom uri
//                .antMatchers(
//                        HttpMethod.GET
//                ).permitAll()
                .antMatchers(
                        HttpMethod.POST,
                        UrlConstants.DRUID_STAT_URIS,
                        UrlConstants.ADMIN_LOGIN_PREFIX + "/**"
                ).permitAll()
                // 对于获取token的rest api要允许匿名访问
//                .antMatchers("/auth/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 添加JWT filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }
}
