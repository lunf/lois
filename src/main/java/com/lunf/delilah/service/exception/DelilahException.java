package com.lunf.delilah.service.exception;

import com.lunf.delilah.service.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DelilahException extends Exception {

    @Getter
    private ErrorCode errorCode;
}
