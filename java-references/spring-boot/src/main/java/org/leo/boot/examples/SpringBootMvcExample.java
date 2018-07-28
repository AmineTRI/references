package org.leo.boot.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Testing spring boot MVC, JPA, Thymleaf, Security application
 * @author fahdessid
 */
@SpringBootApplication(scanBasePackages = {"org.leo.boot"})
@EnableJpaRepositories(basePackages = {"org.leo.boot.data.repository"})
@EntityScan("org.leo.boot.data.model")
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class SpringBootMvcExample {

  public static void main(String[] args) {
      SpringApplication.run(SpringBootMvcExample.class, args);
  }
}
