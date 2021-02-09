package com.sandip.spring.security.springbootsecuritydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, enabled"
//                                        + "from users "
//                                        + "where username = ?")
//                .authoritiesByUsernameQuery("select username, authority"
//                        + "from authorities "
//                        + "where username = ?");
//                .withDefaultSchema()
//                .withUser(
//                                User.withUsername("Sandip")
//                                .password("123")
//                                .roles("USER")
//                )
//                .withUser(
//                        User.withUsername("Rajib")
//                                .password("123")
//                                .roles("ADMIN")
//                );
//        auth.inMemoryAuthentication()
//                .withUser("Sandip")
//                .password("12345")
//                .roles("USER")
//                .and()
//                .withUser("Rajib")
//                .password("321")
//                .roles("ADMIN");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .and()
                .formLogin();
    }
}
