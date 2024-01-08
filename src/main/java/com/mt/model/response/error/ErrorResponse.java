package com.mt.model.response.error;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
    private final String message;
    private final short status;

    public ErrorResponse(String message, short status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public short getStatus() {
        return status;
    }
}
