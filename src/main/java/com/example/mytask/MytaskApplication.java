package com.example.mytask;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mytask.mapper")
public class MytaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(MytaskApplication.class, args);
    }

}
