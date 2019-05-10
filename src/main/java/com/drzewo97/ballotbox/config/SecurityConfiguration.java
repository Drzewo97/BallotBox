package com.drzewo97.ballotbox.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Autowired
    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2_console/**").hasRole("ADMIN")
                .antMatchers("/h2/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();

        // For accessing H2 database ¯\_(ツ)_/¯
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(getUserDetailsService())
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public UserDetailsService getUserDetailsService(){
        final JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        if(!manager.userExists("admin")){
            manager.createUser(User.withUsername("admin").password("test").roles("ADMIN", "USER").build());
        }
        if(!manager.userExists("user")) {
            manager.createUser(User.withUsername("user").password("test").roles("USER").build());
        }
        return manager;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        //TODO: for testing purposes
        return NoOpPasswordEncoder.getInstance();
    }
}
