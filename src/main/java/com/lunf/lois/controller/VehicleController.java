package com.lunf.lois.controller;

import com.lunf.lois.controller.response.RawVehicleData;
import com.lunf.lois.controller.transformer.VehicleActivityTransformer;
import com.lunf.lois.service.VehicleService;
import com.lunf.lois.service.constant.ErrorCode;
import com.lunf.lois.service.exception.DelilahException;
import com.lunf.lois.service.model.VehicleActivityDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            logger.debug("Fail to upload report", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorCode());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping(value = "list_raw_report")
    public ResponseEntity<?> getRawVehicleReports(@RequestParam("page") int page, @RequestParam("limit") int limit,
                                                 @RequestParam("sort") Optional<String> sort) {

        limit = ControllerHelper.convertPageSize(limit);
        Map<String, String> sorts = ControllerHelper.convertSort(sort);

        try {
            List<VehicleActivityDTO> dtoList = vehicleService.findRawVehicleReportPaginated(page, limit, sorts);

            List<RawVehicleData> data = VehicleActivityTransformer.transformToList(dtoList);

            return ResponseEntity.ok(data);
        } catch (DelilahException ex) {
            logger.debug("Fail to get raw report list");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorCode());
        }
    }

    @DeleteMapping(value = "list_raw_report")
    public ResponseEntity<?> deleteRawReports(@RequestParam("ids") String ids) {
        List<Long> dataIdList = ControllerHelper.convertVehicleIdList(ids);

        if (dataIdList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Must included valid Long value.");
        }
        try {
            vehicleService.deleteRawVehicleReport(dataIdList);
        } catch (DelilahException ex) {
            logger.debug("Fail to get raw report list");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrorCode());
        }

        return ResponseEntity.ok(null);
    }

    @PutMapping(value ="merge_raw_report")
    public ResponseEntity<?> mergeRawReport(@RequestParam("ids") String ids) {

        List<Long> dataIdList = ControllerHelper.convertVehicleIdList(ids);

        if (dataIdList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Must included valid Long value.");
        }


        return ResponseEntity.ok(null);
    }

    private boolean isValidRequest(MultipartFile multipartFile) {
        boolean isValid = true;

        if (multipartFile == null || multipartFile.isEmpty()) {
            isValid = false;
        }

        return isValid;
    }
}
