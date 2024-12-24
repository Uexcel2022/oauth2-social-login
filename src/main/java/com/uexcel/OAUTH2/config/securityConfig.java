package com.uexcel.OAUTH2.config;

import com.uexcel.OAUTH2.CsrefTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class securityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(c->c.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/login/**","/logout"));

        http.authorizeRequests(c->c.requestMatchers("/error","/login/**","/logout").permitAll());
        http.authorizeRequests(c->c.requestMatchers("dashboard").authenticated()
                .anyRequest().permitAll());
        http.addFilterAfter(new CsrefTokenFilter(), BasicAuthenticationFilter.class);
        http.oauth2Login(c->c.loginPage("/login").defaultSuccessUrl("/dashboard")
                .failureUrl("/login?error=true"));
        http.formLogin(c->c.loginPage("/login").defaultSuccessUrl("/dashboard")
                .failureUrl("/login?error=true"));
        http.logout(c->c.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true).clearAuthentication(true)
                .deleteCookies("JSESSIONID"));
        return http.build();
    }
}
