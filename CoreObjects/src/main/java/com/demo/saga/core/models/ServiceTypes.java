package com.demo.saga.core.models;

public class ServiceTypes {
    public enum ORDER_STATUS {
        ORDERED,
        CANCELLED,
        // Add more status if needed
    }

    public enum PAYMENT_STATUS {
        PENDING,
        COMPLETE,
        CANCELLED,
        // Add more status if needed
    }

    public enum SHIP_STATUS {
        PENDING,
        COMPLETE,
        CANCELLED,
        // Add more status if needed
    }
}
