package com.project.shopapp_backend.controllers;

import com.project.shopapp_backend.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")

public class CategoryController {
    //Hien thi tat ca cac category
    @GetMapping("")
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    )
    {
        return ResponseEntity.ok(String.format("getAllCategories: page=%d, limit=%d", page, limit));
    }

    @PostMapping("")
    // nếu tham số truyền vào là 1 object thì sao => Data Transfer Object = Request Object
    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                 BindingResult result)
    {
        if (result.hasErrors())
        {
               List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(f -> f.getDefaultMessage())
                    .toList();
               return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("Insert category" + categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id)
    {
        return ResponseEntity.ok("Update category with id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id)
    {
        return ResponseEntity.ok("Delete category with id: " + id);
    }
}
