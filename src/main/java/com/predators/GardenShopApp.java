package com.predators;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class GardenShopApp {

    public static void main(String[] args) {
        SpringApplication.run(GardenShopApp.class, args);
    }
}
//PS C:\Users\jackp\IdeaProjects\finalproject> mvn liquibase:clearCheckSums "-Dliquibase.url=jdbc:postgresql://localhost:5432/garden-shop" "-Dliquibase.username=postgres" "-Dliquibase.password=predators" "-Dliquibase.changeLogFile=src/main/resources/db/db.changelog-master.xml"