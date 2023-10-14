package com.example.gamebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.gamebackend.mapper")
public class GameBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameBackEndApplication.class, args);
    }

}
