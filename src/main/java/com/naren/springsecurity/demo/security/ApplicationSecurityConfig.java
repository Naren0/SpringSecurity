package com.naren.springsecurity.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.naren.springsecurity.demo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*")
                .permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
       UserDetails narenUser =  User.builder()
                .username("naren")
                .password(passwordEncoder.encode("password"))
                .roles(STUDENT.name()) //ROLE_STUDENT
                .build();

       UserDetails adminUser = User.builder()
               .username("admin")
               .password(passwordEncoder.encode("password123"))
               .roles(ADMIN.name())
               .build();


        UserDetails tom = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password123"))
                .roles(ADMINTRAINEE.name())
                .build();
       return new InMemoryUserDetailsManager(narenUser,adminUser,tom);

    }
}
