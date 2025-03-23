package com.example.skillbuild.config;

import com.example.skillbuild.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @details This file SecurityConfig is a Spring Security configuration class.
 *
 * What It Does:
 * @Configuration → Marks this class as a Spring configuration file.
 * @EnableWebSecurity → Enables Spring Security for the application.
 * Defines security rules like authentication, authorization, and session management.
 * This class controls who can access what and how users authenticate (e.g., using sessions, JWT, OAuth, etc.). 🚀
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    AppUserDetailsService appUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http  //requiresChannel(channel -> channel.anyRequest().requiresSecure())
                // Authorization Rules (Restricting Access)
              .csrf(csrf -> csrf.ignoringRequestMatchers("/editProfile"))

              .authorizeHttpRequests((requests) -> requests
                      //Requires authentication (login) for /appUser.
                      .requestMatchers("/appUser", "/appUsers", "/editProfile")
                      .authenticated()
                      .anyRequest().permitAll())



  /* http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                .requestMatchers( "/resetPassword").permitAll()
                .anyRequest().authenticated()

		)*/
                    .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                    .formLogin(form -> form
                        .loginPage("/login-form")
                        //The login form should submit credentials to /myLogin for authentication.
                        .loginProcessingUrl("/myLogin")
                        //search on email
                        .usernameParameter("email")
                        .passwordParameter("password")
                //If login is successful, the user is forwarded to /success-login.
                                    .defaultSuccessUrl("/dashboard", true)
                //If login fails, the user is forwarded to /failure.
                        .failureForwardUrl("/failure")
                //The login page is accessible to everyone.
                        .permitAll()



                )
                // Invalidate users session
                        .logout(logout -> logout
                                .logoutUrl("/logout")
                                .invalidateHttpSession(true)
                //Redirects the user to /login-form with a query parameter (logout=true) after a successful logout.
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessUrl("/")
                //The logout page is accessible to everyone.
                                .permitAll());
        // //The login page is accessible to everyone.
        http.exceptionHandling(ex -> ex
                .accessDeniedPage("/denied")
        );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder.encoder());
        authenticationProvider.setUserDetailsService(appUserDetailsService);

        return authenticationProvider;
    }

}
