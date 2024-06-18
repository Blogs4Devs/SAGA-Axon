package com.demo.saga.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    private String id;
    private String shipmentId;
    private String paymentId;
    private String orderId;
    private String userId;
    private String shipStatus;
    private String comment;

}