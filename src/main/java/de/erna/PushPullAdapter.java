package de.erna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.Banner.Mode.OFF;

@SpringBootApplication
public class PushPullAdapter {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(PushPullAdapter.class);
        application.setBannerMode(OFF);
        application.run(args);
    }
}
