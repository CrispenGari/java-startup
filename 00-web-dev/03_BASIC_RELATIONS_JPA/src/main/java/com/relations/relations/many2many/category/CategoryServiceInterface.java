package com.relations.relations.many2many.category;

import java.util.Collection;

public interface CategoryServiceInterface {
    Category createCategory(Category category);
    Category getCategory(Long id);
    Collection<Category> getAllCategories();
    Collection<Category> getAllCategoriesByIds(Collection<Long> ids);
}
