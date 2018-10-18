package com.lunf.lois.controller;

import java.util.*;

public class ControllerHelper {
    public static final int DEFAULT_PAGE_SIZE = 25;

    public static final String DEFAULT_SORT_DESC = "desc";

    public static final String DEFAULT_SORT_ASC = "asc";

    public static final String DEFAULT_SEPARATOR = ",";

    public static final String DEFAULT_SORT_DIRECTION_SEPARATOR = ":";

    public static final String ORDER_BY_REG_NO = "registration_number";

    public static final String ORDER_BY_CREATED_TIME = "created_at";

    public static final String[] ORDER_BY_LIST = new String[] {
            ORDER_BY_REG_NO, ORDER_BY_CREATED_TIME
    };

    public static int convertPageSize(Integer pageSize) {
        if (pageSize == null || pageSize < 1 || pageSize > 1000) {
            return DEFAULT_PAGE_SIZE;
        }

        return pageSize;
    }

    public static Map<String, String> convertSort(Optional<String> sort) {
        if (sort == null || !sort.isPresent()) {
            return new HashMap<>();
        }

        return sort.map( data -> {

            Map<String, String> mapValue = new HashMap<>();

            if (data.trim().length() < 1) {
                return mapValue;
            }

            String[] keyData = data.split(DEFAULT_SEPARATOR);

            for (String key: keyData) {
                int directionPos = key.indexOf(DEFAULT_SORT_DIRECTION_SEPARATOR);
                if (directionPos > 0) {
                    String value = key.substring(0, directionPos);
                    String direction = key.substring(directionPos + 1, key.length());

                    mapValue.put(value, direction);
                } else {
                    mapValue.put(key, DEFAULT_SORT_ASC);
                }
            }

            return mapValue;
        }).get();
    }

    public static List<Long> convertVehicleIdList(String ids) {
        if(ids == null || ids.trim().length() < 1) {
            return new ArrayList<>();
        }

        String[] idArrays = ids.split(DEFAULT_SEPARATOR);
        List<Long> idList = new ArrayList<>();

        for (String id : idArrays) {
            if (id != null && id.trim().length() > 0) {
                // Check if id is valid
                try {
                    Long longValue = Long.valueOf(id);

                    idList.add(longValue);
                } catch (NumberFormatException ex) {
                    //Ignored and returned empty data
                }
            }
        }

        return idList;
    }
}
