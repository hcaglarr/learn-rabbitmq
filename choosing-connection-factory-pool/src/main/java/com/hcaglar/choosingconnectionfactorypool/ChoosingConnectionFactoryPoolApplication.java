package com.hcaglar.choosingconnectionfactorypool;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class ChoosingConnectionFactoryPoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChoosingConnectionFactoryPoolApplication.class, args);
    }
}
