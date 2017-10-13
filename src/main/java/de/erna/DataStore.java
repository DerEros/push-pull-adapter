package de.erna;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class DataStore {
    private AtomicReference<String> data = new AtomicReference<>("");

    public String getData() {
        return data.get();
    }

    public void setData(String data) {
        this.data.set(data);
    }
}
