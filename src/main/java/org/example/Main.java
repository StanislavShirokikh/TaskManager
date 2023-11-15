package org.example;

import org.example.controller.requests.CreateEpicRequest;
import org.example.dao.TaskDao;
import org.example.dto.Epic;
import org.example.dto.Task;
import org.example.service.manager.Manager;
import org.example.service.manager.ManagerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}