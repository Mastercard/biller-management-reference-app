package com.mastercard.developer;

import com.mastercard.developer.example.BillerManagementApiExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    private BillerManagementApiExample billerManagementApiExample;


    @Autowired
    public Application(BillerManagementApiExample billerManagementApiExample) {
        this.billerManagementApiExample = billerManagementApiExample;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext appContext = SpringApplication.run(Application.class, args);
        appContext.close();
    }

    @Override
    public void run(String... args) {
        try {
            this.billerManagementApiExample.run(args);
        } catch (Exception ex) {
            log.error("<-- APPLICATION ENDED WITH SOME ERROR --> {}", ex.getMessage());
        }
    }
}
