package com.lunf.lois.service;

import com.lunf.lois.service.exception.DelilahException;

import java.io.File;

public interface GpsDataService {
    void processDataImport(File uploadedFile) throws DelilahException;
}
