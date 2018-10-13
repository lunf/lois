package com.lunf.lois.controller;

import com.lunf.lois.service.VehicleService;
import com.lunf.lois.service.constant.ErrorCode;
import com.lunf.lois.service.exception.DelilahException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/vehicles")
@AllArgsConstructor
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    private final VehicleService vehicleService;

    @PostMapping(value = "upload")
    public ResponseEntity<?> uploadReport(@RequestParam("file") MultipartFile multipartFile) {
        if (!isValidRequest(multipartFile)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorCode.FAIL_VALIDATION);
        }



        try {
            vehicleService.uploadVehicleActivityFile(multipartFile);
        } catch (DelilahException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorCode());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    private boolean isValidRequest(MultipartFile multipartFile) {
        boolean isValid = true;

        if (multipartFile == null || multipartFile.isEmpty()) {
            isValid = false;
        }

        return isValid;
    }
}
