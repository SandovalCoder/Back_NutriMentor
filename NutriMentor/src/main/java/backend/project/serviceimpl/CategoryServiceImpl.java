package backend.project.serviceimpl;

import backend.project.entities.Category;
import backend.project.exceptions.InvalidDataException;
import backend.project.exceptions.KeyRepeatedDataException;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.CategoryRepository;
import backend.project.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public boolean isCategoryTypeRegistered(String type) {
        return categoryRepository.findByType(type).isPresent();
    }

    @Override
    public Category updateCategory(Category category) {
        Category categoryFound = findById(category.getId());
        if (categoryFound != null) {
            if (category.getType() != null) {
                if (category.getType().isBlank()) {
                    throw new InvalidDataException("Category type cannot be blank");
                }
                if (!categoryFound.getType().equals(category.getType()) && isCategoryTypeRegistered(category.getType())) {
                    throw new KeyRepeatedDataException("Category type: " + category.getType() + " is already registered");
                }
                categoryFound.setType(category.getType());
            }
            categoryFound.setDescription(category.getDescription());
            return categoryRepository.save(categoryFound);
        } else {
            throw new ResourceNotFoundException("Category with id: " + category.getId() + " cannot be found");
        }
    }

    @Override
    public Category insertCategory(Category category) {
        if (category.getType() == null || category.getType().isBlank()) {
            throw new InvalidDataException("Category type cannot be blank");
        }
        if (isCategoryTypeRegistered(category.getType())) {
            throw new KeyRepeatedDataException("Category type: " + category.getType() + " is already registered");
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category categoryFound = findById(id);
        if (categoryFound != null) {
            categoryRepository.delete(categoryFound);
        } else {
            throw new ResourceNotFoundException("Category with id: " + id + " cannot be found");
        }
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id: " + id + " cannot be found"));
    }

    @Override
    public List<Category> listAll() {
        return categoryRepository.findAll();
    }
}
