package com.blank;

import org.springframework.boot.SpringApplication;

public class TestBlankApplication {

    public static void main(String[] args) {
        SpringApplication.from(BlankApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
