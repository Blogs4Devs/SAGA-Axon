package com.demo.saga.shipment.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.saga.core.events.CompleteShipEvent;
import com.demo.saga.core.models.Shipment;
import com.demo.saga.shipment.repository.ShipmentRepository;

@Component
public class ShipmentEventHandler {

    @Autowired
    private ShipmentRepository repository;

    public void onCompleteShipEvent(CompleteShipEvent event) {
        Shipment shipment = new Shipment();
        try {
            //SIMULATE A SHIPMENT GATEWAY ERROR IF USER ID IS RWE0004
            if(event.getUserId().equalsIgnoreCase("RWE0004")) {
                throw new Exception("Error");
            }
            shipment.setOrderId(event.getOrderId());
            shipment.setPaymentId(event.getPaymentId());
            shipment.setShipmentId(event.getShipmentId());
            shipment.setShipStatus(event.getShipmentStatus());
            shipment.setUserId(event.getUserId());
            shipment.setComment("Shipment Placed");
            repository.save(shipment);
        }catch(Exception e) {
            event.setShipmentStatus("ERROR");
            event.setComment("Shipment gateway error");
        }
    }
}