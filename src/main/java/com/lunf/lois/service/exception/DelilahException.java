package com.lunf.lois.service.exception;

import com.lunf.lois.service.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DelilahException extends Exception {

    @Getter
    private ErrorCode errorCode;
}
