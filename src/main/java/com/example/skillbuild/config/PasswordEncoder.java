package com.example.skillbuild.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/** @details   This unction returns a BCryptPasswordEncoder, which is used to hash passwords securely.
 * The @Bean annotation makes it available for use in other parts of your Spring Boot application.🚀
 */

@Configuration
public class PasswordEncoder {
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
