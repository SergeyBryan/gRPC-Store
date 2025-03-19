package com.sbryan.grpcstore.controller;

import com.sbryan.grpcstore.model.GlobalDelivery;
import com.sbryan.grpcstore.zeebe.ZeebeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final String deliveryProcess = "Process_0wat2l5";

    private final ZeebeService zeebeService;

    @PostMapping(path = "create")
    public ResponseEntity<String> get(@RequestBody GlobalDelivery delivery) {
        if (delivery.getDelivery() != null) {
            var p = zeebeService.startProcess(deliveryProcess, delivery);
            return ResponseEntity.ok().body(p.toString());
        }
        return ResponseEntity.badRequest().build();
    }
}
