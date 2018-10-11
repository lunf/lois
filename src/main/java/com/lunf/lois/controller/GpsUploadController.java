package com.lunf.lois.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gps")
@AllArgsConstructor
public class GpsUploadController {
    private final Logger logger = LoggerFactory.getLogger(GpsUploadController.class);


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadFile) {


        if (uploadFile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        logger.debug(String.format("Uploading file name: %s", uploadFile.getName()));


        try {



        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
                uploadFile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }
}
