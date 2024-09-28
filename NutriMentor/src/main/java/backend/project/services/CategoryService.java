package backend.project.services;

import backend.project.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category> listAll();

    Category insertCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long id);

    Category findById(Long id);
}
