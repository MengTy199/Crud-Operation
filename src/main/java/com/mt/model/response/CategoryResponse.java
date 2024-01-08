package com.mt.model.response;

import com.mt.infrastructure.model.response.BaseResponse;
import com.mt.model.entity.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public class CategoryResponse extends BaseResponse {
    @Schema(example = "168")
    private final Long id;
    @Schema(example = "Koko")
    private final String name;
    @Schema(example = "Nice one")
    private final String description;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CategoryResponse(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static CategoryResponse fromEntity(CategoryEntity entity){
        return new CategoryResponse(
            entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}
