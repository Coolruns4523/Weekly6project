package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected  void configure (HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/list").permitAll()
                .antMatchers("/add").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser("Tony").password(encoder.encode("pa$$word"))
                .authorities("USER")
                .and()
                .withUser("theuser").password(encoder.encode("pas$$word"))
                .authorities("USER")
                .and()
                .withUser("Brandon").password(encoder.encode("ca$h"))
                .authorities("ADMIN")
                .and()
                .passwordEncoder(encoder);


    }
}
