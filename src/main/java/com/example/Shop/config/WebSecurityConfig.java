package com.example.Shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    private final DataSource primaryDataSource;

    public WebSecurityConfig(DataSource primaryDataSource) {
        this.primaryDataSource = primaryDataSource;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{
        return security
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests()
                .antMatchers("/login", "/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/home", true).permitAll()
                .and()
                .logout().logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .userDetailsService(userDetailsService()).build();
    }


//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.jdbcAuthentication().dataSource().
//                passwordEncoder(NoOpPasswordEncoder.getInstance()).
//                usersByUsernameQuery("select login, password, 0 from user where login=?").
//                authoritiesByUsernameQuery("login, role from user where login = ?");
//
//        auth.jdbcAuthentication().
//                withUser("hr").
//                password("hr").
//                roles("HR");
//    }

    @Bean
    UserDetailsService userDetailsService(){
        JdbcDaoImpl service = new JdbcDaoImpl();
        service.setDataSource(primaryDataSource);
        service.setUsersByUsernameQuery("select login, password, true from user where login = ?");
        service.setAuthoritiesByUsernameQuery("select login, role from user where login = ?");
        return service;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
