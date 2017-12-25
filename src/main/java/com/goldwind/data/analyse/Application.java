package com.goldwind.data.analyse;

/**
 * Author: yuqing
 *
 * All the methods start from this place.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Application{

    public static void main(String[] args) {
        System.out.println("Start word count app......");
        SpringApplication.run(Application.class, args);
    }

}