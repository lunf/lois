package com.lunf.lois.service.impl;

import com.lunf.lois.service.GpsDataService;
import com.lunf.lois.service.constant.ErrorCode;
import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.model.VehicleActivityDTO;
import com.lunf.lois.utilities.DateTimeHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class GpsDataServiceImpl implements GpsDataService {

    private final Logger logger = LoggerFactory.getLogger(GpsDataServiceImpl.class);

    private static final String START_DATA_ROW = "#startLoop";
    private static final String END_DATA_ROW = "#endLoop";

    private static final String datePattern = "dd/MM/yyyy";
    private static final String hourMinutePattern = "HH:mm";
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
    private static DateTimeFormatter hourMinuteFormatter = DateTimeFormatter.ofPattern(hourMinutePattern);

    @Override
    public void processDataImport(File uploadedFile) throws DelilahException {

        logger.debug("Testing --- for parsing data " + uploadedFile.getName());
        List<VehicleActivityDTO> carActivities = new ArrayList<>();
        try {
            Workbook wb = null;
            if (!uploadedFile.exists()) {
                wb = new HSSFWorkbook();
            } else {
                wb = WorkbookFactory.create(uploadedFile);
            }

            DataFormatter formatter = new DataFormatter();
            Sheet sheet1 = wb.getSheetAt(0);
            boolean isRealData = false;
            for (Row row : sheet1) {

                VehicleActivityDTO vehicleActivityDTO = new VehicleActivityDTO();
                String cellValue = null;
                for (Cell cell : row) {

                    // get the text that appears in the cell by getting the cell value
                    // and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
                    cellValue = formatter.formatCellValue(cell);

                    // Finding the start of useful data
                    if (cellValue.equalsIgnoreCase(START_DATA_ROW)) {
                        isRealData = true;
                        continue;
                    }

                    // Finding the end of useful data
                    if (cellValue.equalsIgnoreCase(END_DATA_ROW)) {
                        isRealData = false;
                        break;
                    }

                    // In case this is not a real data then ignored it
                    if (!isRealData) {
                        continue;
                    }

                    // Parse data
                    switch (cell.getColumnIndex()) {
                        case 3: {
                            vehicleActivityDTO.setRegistrationNumber(cellValue);
                            break;
                        }
                        case 5: {
                            ZonedDateTime date = DateTimeHelper.convertToDateTime(cellValue, dateFormatter);
                            vehicleActivityDTO.setCreatedAt(date);
                            break;
                        }
                        case 6: {
                            LocalTime time = LocalTime.parse(cellValue, hourMinuteFormatter);
                            vehicleActivityDTO.setDepartedTime(time);
                            break;
                        }
                        case 7: {
                            LocalTime time = LocalTime.parse(cellValue, hourMinuteFormatter);
                            vehicleActivityDTO.setArrivalTime(time);
                            break;
                        }
                        case 8: {
                            vehicleActivityDTO.setOrigin(cellValue);
                            break;
                        }
                        case 9: {
                            vehicleActivityDTO.setDestination(cellValue);
                            break;
                        }
                        case 10: {
                            LocalTime time = LocalTime.parse(cellValue, hourMinuteFormatter);
                            int hourMinutes = Math.round(time.getHour() * 60) + time.getMinute();

                            vehicleActivityDTO.setTotalRunningInMinute(hourMinutes);
                            break;
                        }
                        case 12: {
                            LocalTime time = LocalTime.parse(cellValue, hourMinuteFormatter);
                            int hourMinutes = Math.round(time.getHour() * 60) + time.getMinute();

                            vehicleActivityDTO.setTotalPauseInMinute(hourMinutes);
                            break;
                        }
                        case 15: {
                            Double distance = Double.parseDouble(cellValue.replace(",","."));
                            vehicleActivityDTO.setDistanceWithGps(distance);

                            break;
                        }
                        case 20: {
                            Integer startStop = Integer.parseInt(cellValue);
                            vehicleActivityDTO.setNumberOfStopStart(startStop);

                            break;
                        }
                        case 27: {
                            Double maxSpeed = Double.parseDouble(cellValue.replace(",","."));
                            vehicleActivityDTO.setMaxSpeed(maxSpeed);
                        }
                        case 28: {
                            Double averageSpeed = Double.parseDouble(cellValue);
                            vehicleActivityDTO.setAverageSpeed(averageSpeed);
                        }
                    }
                }

                if (isRealData && !cellValue.equalsIgnoreCase(START_DATA_ROW)) {
                    carActivities.add(vehicleActivityDTO);
                }
            }
        } catch (IOException ex) {
            logger.error("Fail to parse Excel uploaded file",ex);
            throw new DelilahException(ErrorCode.PARSING_DATA_ERROR);
        }

        logger.debug("Parsing data completed with size " + carActivities.size());
    }

}
