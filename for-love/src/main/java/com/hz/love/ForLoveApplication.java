package com.hz.love;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = { "com.hz.love.feign" })
@SpringBootApplication
public class ForLoveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForLoveApplication.class, args);
    }

}
