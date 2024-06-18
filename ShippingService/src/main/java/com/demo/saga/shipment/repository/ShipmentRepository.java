package com.demo.saga.shipment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.demo.saga.core.models.Shipment;

public interface ShipmentRepository extends MongoRepository<Shipment, String> {
    Shipment findByShipmentId(String shipmentId);
}