package com.astraval.iotroot.event;

import org.springframework.context.ApplicationEvent;

public class UserCreatedEvent extends ApplicationEvent {
    private final String userId;

    public UserCreatedEvent(Object source, String userId) {
        super(source);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
