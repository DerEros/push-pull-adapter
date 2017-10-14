package de.erna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.boot.Banner.Mode.OFF;

@RestController
@SpringBootApplication
public class PushPullAdapter {
    private static Logger log = LoggerFactory.getLogger(PushPullAdapter.class);

    private AtomicReference<String> data = new AtomicReference<>("");
    private SimpMessagingTemplate template;

    @Autowired
    public PushPullAdapter(SimpMessagingTemplate template) {
        this.template = template;
    }

    @GetMapping("/")
    @ResponseBody
    public String getData() {
        return data.get();
    }

    @PostMapping("/")
    public void storeData(@RequestBody String newData) {
        log.debug("Got data: {}", data);
        this.data.set(newData);
        template.convertAndSend("/topic/data", newData);
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(PushPullAdapter.class);
        application.setBannerMode(OFF);
        application.run(args);
    }
}
