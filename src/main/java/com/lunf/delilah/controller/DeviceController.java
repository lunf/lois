package com.lunf.delilah.controller;

import com.lunf.delilah.controller.request.DeviceRequest;
import com.lunf.delilah.controller.transformer.DeviceRequestTransformer;
import com.lunf.delilah.service.DeviceService;
import com.lunf.delilah.service.exception.DelilahException;
import com.lunf.delilah.service.model.DeviceDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
@AllArgsConstructor
public class DeviceController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private final DeviceService deviceService;

    @PostMapping
    public ResponseEntity<?> createDevice(@RequestBody DeviceRequest deviceRequest) {
        if(!isValidRequest(deviceRequest)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        DeviceDTO deviceDTO = DeviceRequestTransformer.transform(deviceRequest);

        try {
            deviceService.insertDevice(deviceDTO);
        } catch (DelilahException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorCode());
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private boolean isValidRequest(DeviceRequest deviceRequest) {
        boolean isValid = true;

        if (deviceRequest.getNotification_id() == null || deviceRequest.getNotification_id().isEmpty()) {
            isValid = false;
        }

        return isValid;
    }
}
