package com.alekseenko.lms.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

  @Configuration
  public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//TODO: expand configuration
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
          .authorizeRequests()
          .antMatchers("/admin/**").hasAnyRole("OWNER", "ADMIN", "TUTOR")
          .antMatchers("/**").permitAll()
          .and()
          .formLogin()
          .defaultSuccessUrl("/course")
          .and()
          .exceptionHandling()
          .accessDeniedPage("/access_denied");
    }
  }
}
