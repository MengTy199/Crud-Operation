package com.mt.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class RestoreCategoryReqest implements Serializable {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Coca", maxLength = 30)
    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name cann't be Empty")
    @Size(max = 30, message = "Name Cannot be bigger than 30 characters")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
