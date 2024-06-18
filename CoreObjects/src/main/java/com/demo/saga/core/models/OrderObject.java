package com.demo.saga.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderObject {

    private String productId;
    private String userId;
    private String quantity;
    private String price;
}