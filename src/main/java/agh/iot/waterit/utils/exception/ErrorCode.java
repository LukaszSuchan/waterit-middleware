package agh.iot.waterit.utils.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_FOUND(404),
    VALIDATION_ERROR(400),
    INTERNAL_ERROR(500);

    private final int httpStatus;

    ErrorCode(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
