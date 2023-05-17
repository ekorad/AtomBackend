package com.atom.application.configs;

import java.util.Arrays;

import com.atom.application.filters.JWTAuthorizationFilter;
import com.atom.application.handlers.UsernamePasswordAuthenticationSuccessHandler;
import com.atom.application.others.Http401UnauthorizedEntryPoint;
import com.atom.application.services.WebUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Profile("dev")
@EnableWebSecurity(debug = true)
@Configuration
public class DevWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Http401UnauthorizedEntryPoint unauthorizedEntryPoint;
    @Autowired
    private UsernamePasswordAuthenticationSuccessHandler authSuccessHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new WebUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter() {
        return new JWTAuthorizationFilter();
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter(authenticationManager());
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/users/authenticate", "POST"));
        filter.setAuthenticationSuccessHandler(authSuccessHandler);
        return filter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "TRACE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Origin", "X-Auth-Token", "Accept", "X-Request-With", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
        .and()
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeRequests()
            .antMatchers(HttpMethod.POST, "/users/authenticate", "/users/add", "/users/pass-reset-request", "/orders/add").permitAll()
            .antMatchers(HttpMethod.GET, "/users/check*", "/products*").permitAll()
            .antMatchers("/users/**").authenticated()
        .and().exceptionHandling()
            .authenticationEntryPoint(unauthorizedEntryPoint)
        .and()
            .addFilterAfter(usernamePasswordAuthenticationFilter(), LogoutFilter.class)
            .addFilterAfter(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
