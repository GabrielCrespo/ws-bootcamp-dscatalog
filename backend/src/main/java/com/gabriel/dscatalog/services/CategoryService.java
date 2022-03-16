package com.gabriel.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.dscatalog.dto.CategoryDTO;
import com.gabriel.dscatalog.entities.Category;
import com.gabriel.dscatalog.repositories.CategoryRepository;
import com.gabriel.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		return repository.findAll()
				.stream()
				.map(category -> new CategoryDTO(category))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj =  repository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Category not found"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO create(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		
		return new CategoryDTO(entity);
	}
	
}
