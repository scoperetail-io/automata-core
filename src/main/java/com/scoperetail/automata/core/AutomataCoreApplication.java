package com.scoperetail.automata.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.scoperetail"})
@EntityScan(basePackages = {"com.scoperetail"})
@EnableJpaRepositories(basePackages = {"com.scoperetail"})
public class AutomataCoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(AutomataCoreApplication.class, args);
  }
}
