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

//import com.example.quizapplication.config.CustomAuthSuccessHandler;
//import com.example.quizapplication.config.CustomDetailsService;
import com.example.realestateproject.service.UserInfoUserDetailsService;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
////	@Lazy
////	private final JwtAuthenticationFilter jwtAuthenticationFilter;
////	
////	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
////		this.jwtAuthenticationFilter=jwtAuthenticationFilter;
////	}
//	
//	 @Autowired
//	    private  UserInfoUserDetailsService userDetailsService;
//	
////	@Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf(csrf -> csrf.disable())
////                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .authorizeHttpRequests(auth -> auth
////        
////        .requestMatchers("/login","/register").permitAll()
////        .anyRequest().authenticated())
////       .httpBasic(Customizer.withDefaults());
////        http.addFilterBefore(jwtAuthenticationFilter,org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
////      return http.build();
////    }
//	
//	 @Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	
//
//	        http.csrf(csrf-> csrf.disable())
//	                .authorizeHttpRequests(auth->auth
//	                                .requestMatchers("/register", "/process_register", "/loginpage", "/css/**", "/images/**").permitAll() //public accessible
//	                              
//	                                .requestMatchers("/admin/**").hasRole("ADMIN")
//	                                .requestMatchers("/customer/**").hasRole("CUSTOMER")
//	                                .anyRequest().authenticated()
//	                        ).formLogin(form->form
//	                        .loginPage("/loginpage")
//	                        .loginProcessingUrl("/loginpage")
//	                        .successHandler((request, response, authentication) -> {
//	                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//	                            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//	                                response.sendRedirect("/admin/home");
//	                            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
//	                                response.sendRedirect("/customer/home");
//	                            } else {
//	                                response.sendRedirect("/loginpage?error");
//	                            }
//	                        })
//	                        .permitAll()
//	                    )
//	                    .logout(logout -> logout
//	                        .logoutSuccessUrl("/loginpage?logout")
//	                        .permitAll()
//	                    );
//	                        
//	                        
//	        return http.build();
//	    }
//
//
////	@Bean
////	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
////		return new InMemoryUserDetailsManager(
////				User.withUsername("john").password(passwordEncoder().encode("password"))
////				.roles("USER").build(),
////				User.withUsername("admin").password(passwordEncoder().encode("admin"))
////				.roles("ADMIN").build());
////	} 
//	@Bean
//    public UserDetailsService userDetailsService(){
//        return  new UserInfoUserDetailsService();
//    }
//	
//	
//	
////    @Bean
////    public UserDetailsService userDetailsService() {
////        UserDetails user = User.builder()
////                .username("user")
////                .password(passwordEncoder().encode("user123"))
////                .roles("USER")
////                .build();
////        UserDetails admin = User.builder()
////                .username("admin")
////                .password(passwordEncoder().encode("admin123"))
////                .roles("ADMIN")
////                .build();
////        return new InMemoryUserDetailsManager(user,admin);
////    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    
////    @Bean
////    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
////    		PasswordEncoder passwordEncoder) {
////    	DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
////    	provider.setUserDetailsService(userDetailsService);
////    	provider.setPasswordEncoder(passwordEncoder);
////		return new ProviderManager(provider);
////    	
////    }
//    
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return  provider;
//    }
//
//
////    @Bean
////    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
////        return  configuration.getAuthenticationManager();
////    }
//}

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserInfoUserDetailsService userDetailsService;
    
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


        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth->auth
                                .requestMatchers("/register", "/process_register", "/login", "/css/**", "/images/**").permitAll() //public accessible
                              
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/customer/**").hasRole("CUSTOMER")
                                .requestMatchers("/").authenticated()
                                .anyRequest().authenticated()
                        ).formLogin(form->form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                                response.sendRedirect("/");
                            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
                                response.sendRedirect("/");
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


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .authorizeHttpRequests()
//                .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
//                .requestMatchers("/admin/**").hasAuthority("ADMIN")
//                .requestMatchers("/user/**").hasAuthority("CUSTOMER")
//                .anyRequest().authenticated()
//            .and()
//            .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .successHandler(new CustomAuthSuccessHandler())
//                .defaultSuccessUrl("/user", true)
//                .failureUrl("/login?error=true")
//            .and()
//            .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout=true");
//        return http.build();
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }
}    
