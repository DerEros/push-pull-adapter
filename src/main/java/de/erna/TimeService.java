package de.erna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class TimeService {
    private static final int SLEEP_TIME = 1000;

    private SimpMessagingTemplate template;
    private AtomicBoolean stopFlag;
    private Thread timeThread;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

    public class Time {
        String time;

        Time(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }
    }

    @Autowired
    public TimeService(SimpMessagingTemplate template) {
        this.template = template;
        stopFlag = new AtomicBoolean(false);
    }

    @PostConstruct
    public void startTimeService() {
        stopFlag.set(false);

        timeThread = new Thread(() -> {
                while (!stopFlag.get()) {
                    String date = sdf.format(new Date());
                    Time t = new Time(date);

                    template.convertAndSend("/topic/time", t);

                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        stopFlag.set(true);
                    }
                }
        });

        timeThread.start();
    }

    @PreDestroy
    public void stopTimeService() {
        stopFlag.set(true);
    }
}
