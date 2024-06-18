package com.demo.saga.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderCreateEvent {
    private String orderId;
    private String productId;
    private String userId;
    private String quantity;
    private String price;
    private String orderStatus;
    private String comment;
}
