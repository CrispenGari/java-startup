package com.relations.relations.many2many.question;

import com.relations.relations.many2many.category.Category;
import com.relations.relations.many2many.category.CategoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
@RestController
@RequestMapping(path = "/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {
    private  final CategoryService categoryService;
    private  final QuestionService questionService;
    @PostMapping(path="/add")
    public ResponseEntity<Question> createCategory(@RequestBody QuestionInput input){

        Collection<Category>  categories = this.categoryService.getAllCategoriesByIds(input.getCategoryIds());

        System.out.println("------------------------");
        System.out.println(categories);
        System.out.println("------------------------");
        input.getQuestion().setCategories(categories);
        return ResponseEntity.status(201).body(this.questionService.createQuestion(input.getQuestion()));
    }

    @GetMapping(path="/all")
    public ResponseEntity<Collection<Question>> getCategories(){
        return ResponseEntity.status(200).body(this.questionService.getQuestions());
    }

    @GetMapping(path="/one/{questionId}")
    public ResponseEntity<Question> getCategory(@PathVariable("questionId") Long questionId){
        return ResponseEntity.status(200).body(this.questionService.getQuestion(questionId));
    }
}

@Data
class QuestionInput{
    private Question question;
    private Collection<Long> categoryIds;
}