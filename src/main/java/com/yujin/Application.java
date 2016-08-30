package com.yujin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.yujin")
@EntityScan(basePackages = "com.yujin.model")
//@EnableJpaRepositories(basePackages = "com.yujin.dao")
public class Application {

    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args);
    }
}
