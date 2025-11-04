package com.example.realestateproject.com.example.realestateproject.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.realestateproject.service.UserInfoUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserInfoUserDetailsService userDetailsService;
    
    public SecurityConfig(UserInfoUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
        http
        .securityContext(securityContext -> securityContext.requireExplicitSave(false))
        .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth->auth
                                .requestMatchers("/register", "/process_register", "/login", "/css/**", "/images/**").permitAll() //public accessible
                              
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/customer/**").hasRole("CUSTOMER")
                                .requestMatchers("/search").authenticated()
                                .requestMatchers("/customer/create").permitAll()
                                .anyRequest().authenticated()
                        ).formLogin(form->form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                                response.sendRedirect("/admindashboard");
                            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
                                response.sendRedirect("/userdashboard");
                            } else {
                                response.sendRedirect("/login?error");
                            }
                        })
                        .permitAll()
                    )
                    .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                    );
                        
                        
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());

    }
}    
