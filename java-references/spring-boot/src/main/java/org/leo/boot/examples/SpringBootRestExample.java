package org.leo.boot.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Testing Spring Boot Rest, Mongodb application
 * @author fahdessid
 */
//when SpringBootApplication annotation is used, the ScanPackage is implicitly used for the current package
@SpringBootApplication(scanBasePackages = {"org.leo.boot.rest.api"})
//needed if the Mongodb repositories are not in the same package/sub package of the application class
@EnableMongoRepositories(basePackages = {"org.leo.boot.data.repository"})
//we only want to test rest api against mongodb here, so we disable Datasource and Security integration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class, SecurityAutoConfiguration.class})
@RestController
public class SpringBootRestExample {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootRestExample.class, args);
  }
  
  @RequestMapping("/input")
  String getInputString(String inputString) {
    return inputString;
  }
  
  @RequestMapping("/")
  String getString() {
    return "Hello Spring world";
  } 
  
  @RequestMapping("/api/greet")
  String greet() {
    return "Hello world";
  } 
}
