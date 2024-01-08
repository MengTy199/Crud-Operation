package com.mt.controller.BackEnd;
import com.mt.exception.BedRequestException;
import com.mt.infrastructure.model.response.BaseResponse;
import com.mt.infrastructure.model.response.body.BaseBodyResponse;
import com.mt.model.entity.CategoryEntity;
import com.mt.model.request.CategoryReqest;
import com.mt.model.request.RestoreCategoryReqest;
import com.mt.model.response.CategoryResponse;
import com.mt.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Tag(name = "BackEnd Category Controller ", description = "Controller for admin manage category ")
@RestController
@RequestMapping("/api/cateogry")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @Operation(summary = "Endpoint for admin create a category", description = "Admin can creating a category by using this endpoint",
    responses = {
            @ApiResponse(description = "Success", responseCode = "201", content  = @Content(schema = @Schema(implementation =CategoryResponse.class), mediaType = "application/json")),
            @ApiResponse(description = "Error", responseCode = "400-500", content = @Content(schema =@Schema(implementation = ErrorResponse.class), mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<BaseBodyResponse> create(@Valid @RequestBody CategoryReqest reqest) throws Exception {
        CategoryEntity entity = this.categoryService.create(reqest);

        return BaseBodyResponse.CreateSuccess(CategoryResponse.fromEntity(entity), "create successfully");
    }

    @GetMapping
    public ResponseEntity<BaseBodyResponse> findAll(
            //@RequestParam(name = "q", required = false) String q,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "IsPage", required = false, defaultValue = "true") Boolean IsPage,
            @RequestParam(name = "sort", required = false, defaultValue = "id:desc") String sort,
            @RequestParam(name = "isTrash", required = false, defaultValue = "false")boolean isTrash,
            @RequestParam Map<String, String> reqParam) throws BedRequestException {
        System.out.println("Hello : " + reqParam.keySet());
        //List<CategoryResponse> category = this.categoryService.findAll(q, limit, page, Objects.equals(IsPage, "true"), sort,reqParam).stream().map(CategoryResponse::fromEntity).toList();
        Page<BaseResponse> category = this.categoryService.findAll(limit, page, /*Objects.equals(IsPage, "true")*/ IsPage, sort, isTrash, reqParam).map(CategoryResponse::fromEntity);
        return BaseBodyResponse.success(category, "Success");
    }
    @GetMapping("/{CategoryId}")
    public ResponseEntity<BaseBodyResponse> findone(@PathVariable(name ="CategoryId") Long id) throws Exception {
        CategoryEntity data = this.categoryService.findone(id);
        return BaseBodyResponse.success(CategoryResponse.fromEntity(data), "Find one successfully");
    }

    @PutMapping("/{CategoryId}")
    public ResponseEntity<BaseBodyResponse> update(@RequestBody CategoryReqest reqest, @PathVariable(name="CategoryId") Long id) throws Exception {
        CategoryEntity entity = this.categoryService.update(reqest, id);
        return BaseBodyResponse.success(CategoryResponse.fromEntity(entity), "Update successfully");
    }

    @DeleteMapping("/{CategoryId}")
    public ResponseEntity<BaseBodyResponse> delete(@PathVariable(name="CategoryId") Long id) throws Exception {
        CategoryEntity entity = this.categoryService.delete(id);
        return BaseBodyResponse.success(CategoryResponse.fromEntity(entity), "delete successfully");
    }

    @PutMapping("/retore/{CategoryId}")
    public ResponseEntity<BaseBodyResponse> restores (@RequestBody RestoreCategoryReqest req, @PathVariable(name = "CategoryId") Long id) throws Exception {
        CategoryEntity entity = this.categoryService.restore(id, req);
        return BaseBodyResponse.success(CategoryResponse.fromEntity(entity), "Restored successfully");
    }
}
