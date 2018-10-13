package com.lunf.lois.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(SecureUserController.class);


    @RequestMapping(method = RequestMethod.GET, value = "/{orderName}")
    public ResponseEntity<?> getOrderByName(@PathVariable("orderName") String orderName) {

        return ResponseEntity.ok(orderName);
    }

}
