package com.astraval.iotroot.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    @EventListener
    public void handleUserCreated(UserCreatedEvent event) {
        System.out.println("📢 New user registered: " + event.getUserId());
    }
}
