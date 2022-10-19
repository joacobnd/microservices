package com.n0tification;

import org.springframework.beans.factory.annotation.Value;

public class NotificationConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.notification.queue}")
    private String notificationQueue;

    @Value("${rabbitmq.routing-key.internal.notification}")
    private String internalNotificationRoutingKey;



    public String getInternalExchange() {
        return internalExchange;
    }

    public String getNotificationQueue() {
        return notificationQueue;
    }

    public String getInternalNotificationRoutingKey() {
        return internalNotificationRoutingKey;
    }
}
