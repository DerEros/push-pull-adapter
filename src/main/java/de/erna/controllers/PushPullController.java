package de.erna.controllers;

import de.erna.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
public class PushPullController {
    private final Logger log = LoggerFactory.getLogger(PushPullController.class);
    private final DataStore dataStore;

    public PushPullController(@Autowired DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @GetMapping("/")
    @ResponseBody
    public String getData() {
        return dataStore.getData();
    }

    @PostMapping("/")
    public void storeData(@RequestBody String newData) {
        log.debug("Got data: {}", newData);
        this.dataStore.setData(newData);
    }
}
