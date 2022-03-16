package com.gabriel.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.dscatalog.dto.CategoryDTO;
import com.gabriel.dscatalog.entities.Category;
import com.gabriel.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	public List<CategoryDTO> findAll() {
		return repository.findAll()
				.stream()
				.map(category -> new CategoryDTO(category))
				.collect(Collectors.toList());
	}

	public CategoryDTO findById(Long id) {
		Optional<Category> obj =  repository.findById(id);
		Category entity = obj.get();
		return new CategoryDTO(entity);
	}
	
}
