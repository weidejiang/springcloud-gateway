package com.manulife.ap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.manulife.ap"})
@EnableDiscoveryClient
public class SpringCloudGetwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGetwayApplication.class, args);
    }


}
