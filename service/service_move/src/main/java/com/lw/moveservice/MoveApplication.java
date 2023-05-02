package com.lw.moveservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lw"})//加载配置
@MapperScan("com.lw.moveservice.mapper")
@EnableSwagger2
//@EnableDiscoveryClient
//@EnableFeignClients
public class MoveApplication {
    public static void main(String[] args) {
        SpringApplication.run(MoveApplication.class, args);
    }

}
