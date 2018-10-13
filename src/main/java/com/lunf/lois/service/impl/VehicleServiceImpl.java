package com.lunf.lois.service.impl;

import com.lunf.lois.data.primary.mapper.LfVehicleActivityMapper;
import com.lunf.lois.data.primary.transformer.LfVehicleTransformer;
import com.lunf.lois.service.ExcelDataService;
import com.lunf.lois.service.VehicleService;
import com.lunf.lois.service.constant.ErrorCode;
import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.model.VehicleActivityDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);


    private static final String START_DATA_ROW = "#startLoop";
    private static final String END_DATA_ROW = "#endLoop";

    private static final String datePattern = "dd/MM/yyyy";
    private static final String hourMinutePattern = "HH:mm";
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
    private static DateTimeFormatter hourMinuteFormatter = DateTimeFormatter.ofPattern(hourMinutePattern);

    @Autowired
    private LfVehicleActivityMapper lfVehicleActivityMapper;

    private final ExcelDataService excelDataService;

    @Override
    public void uploadVehicleActivityFile(MultipartFile multipartFile) throws DelilahException {
        List<VehicleActivityDTO> activityList = excelDataService.processVehicleData(multipartFile);

        if (activityList == null || activityList.size() < 1) {
            throw new DelilahException(ErrorCode.DATA_EMPTY);
        }

        List transformedList = LfVehicleTransformer.transformList(activityList);

        lfVehicleActivityMapper.insertBatch(transformedList);

    }


}
