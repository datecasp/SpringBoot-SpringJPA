package com.datecasp.SpringBootSpringJPA.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/User").hasAnyRole("USER","ADMIN")
                .antMatchers("/Admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    //Usuarios En-Memoria para pruebas
    //Sustituir por usuarios en BD
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("usuario").password(passwordEncoder().encode("1234")).roles("USER")
                .and()
                .withUser("jefe").password(passwordEncoder().encode("1234")).roles("ADMIN");
    }


//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        UserDetails usuario = User.builder()
//                .username("usuario")
//                .password(passwordEncoder().encode("1234"))
//                .roles("USER")
//                .build();
//        UserDetails jefe = User.builder()
//                .username("jefe")
//                .password(passwordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .build();
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager(usuario, jefe);
//
//        return userDetailsManager;
//
//    }


    //Codificador de password
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
