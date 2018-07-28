package org.leo.boot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration class, analaog to a Spring ApplicationContext where we define third party 
 * components to inject into the app
 * @author fahdessid
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  /**
   * Injecting BCryptPasswordEncoder as a third party component
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder;
  }
}
