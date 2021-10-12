package com.relations.relations.many2many.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(path="/add")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        return ResponseEntity.status(201).body(this.categoryService.createCategory(category));
    }

    @GetMapping(path="/all")
    public ResponseEntity<Collection<Category>> getCategories(){
        return ResponseEntity.status(200).body(this.categoryService.getAllCategories());
    }

    @GetMapping(path="/one/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(200).body(this.categoryService.getCategory(categoryId));
    }
}
