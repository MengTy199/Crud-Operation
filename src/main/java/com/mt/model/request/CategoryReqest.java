package com.mt.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mt.model.entity.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class CategoryReqest implements Serializable {
//    @Schema(name = "nm")//change only on open api doc
//    @JsonProperty("nm")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Coca", maxLength = 30)
    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name cann't be Empty")
    @Size(max = 30, message = "Name Cannot be bigger than 30 characters")
    private String name;
    @Size(max = 100, message = "Description Cann't bigger than 100 characters!")
    @Schema(example = "Good deals", maxLength = 100)
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEntity toEntity(){
        CategoryEntity category = new CategoryEntity();
        category.setName(this.name);
        category.setDescription(this.description);
        return category;
    }
}
