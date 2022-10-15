package com.clients.n0tification;

public record NotificationRequest(
        Integer toCustomerId,
        String toCustomerName,
        String message) {
}
