package com.gotoubun.weddingvendor.security;

import com.gotoubun.weddingvendor.service.common.CustomAccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.gotoubun.weddingvendor.security.SecurityConstants.*;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private CustomAccountDetailsService customUserDetailsService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().sameOrigin() //To enable H2 Database
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers(SIGN_UP_URLS).permitAll()
                .antMatchers(SIGN_UP_ADMIN_URLS).permitAll()
                .antMatchers(SIGN_UP_CUSTOMER_URLS).permitAll()
                .antMatchers(SIGN_UP_VENDOR_URLS).permitAll()
                .antMatchers(SINGLE_SERVICE_URLS).permitAll()
                .antMatchers(SERVICE_PACK_URLS).permitAll()
                .antMatchers(SIGN_UP_KOL_URLS).permitAll()
                .antMatchers(BLOG_URLS).permitAll()
                .antMatchers(BUDGET_URL).permitAll()
                .antMatchers(SINGLE_CATEGORY_URLS).permitAll()
                .antMatchers(PACKAGE_CATEGORY_URLS).permitAll()
                .antMatchers(GUEST_URLS).permitAll()
                .antMatchers(H2_URL).permitAll()
                .antMatchers(PAYMENT_URLS).permitAll()
                .antMatchers(SWAGGER).permitAll()
                .antMatchers(PAYMENT_RESULTS_URLS).permitAll()
                .antMatchers(SWAGGER).permitAll()
                .antMatchers(HOME_FEEDBACK).permitAll()
                .antMatchers(FACEBOOK_LOGIN).permitAll()
                .antMatchers(GOOGLE_LOGIN).permitAll()
//                .antMatchers(CONTEXT_PATH).permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}