package com.relations.relations.many2many.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceInterface{

    private  final CategoryRepository repository;
    @Override
    public Category createCategory(Category category) {
        return this.repository.save(category);
    }
    @Override
    public Category getCategory(Long id) {
        return this.repository.findById(id).orElseThrow(()-> new RuntimeException("no category of that id"));
    }
    @Override
    public Collection<Category> getAllCategories() {
        return this.repository.findAll();
    }

    @Override
    public Collection<Category> getAllCategoriesByIds(Collection<Long> ids) {
        return this.repository.findAllById(ids);
    }
}
