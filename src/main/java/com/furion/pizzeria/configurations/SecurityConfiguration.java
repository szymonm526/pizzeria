package com.furion.pizzeria.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/")
        .permitAll()
            .and().authorizeRequests().antMatchers("/kucharz/**")
            .hasRole("KUCHARZ").and()
             .authorizeRequests().antMatchers("/kelner/**")
             .hasRole("KELNER").and()
            .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and()
                .formLogin().permitAll()
                    .and()
                .logout().permitAll();


        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("kucharz").password(passwordEncoder().encode("alamakota")).roles("KUCHARZ")
                .and()
                .withUser("kelner").password(passwordEncoder().encode("alamakota")).roles("KELNER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}