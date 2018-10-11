package com.lunf.lois.utilities;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class DataConverter {

    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convertFile = new File(multipart.getOriginalFilename());
        multipart.transferTo(convertFile);


        return convertFile;
    }
}
