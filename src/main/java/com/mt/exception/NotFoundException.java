package com.mt.exception;

import java.io.Serializable;

public class NotFoundException  extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
