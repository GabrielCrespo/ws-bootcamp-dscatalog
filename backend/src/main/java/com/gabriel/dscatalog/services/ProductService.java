package com.gabriel.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.dscatalog.dto.ProductDTO;
import com.gabriel.dscatalog.entities.Product;
import com.gabriel.dscatalog.repositories.ProductRepository;
import com.gabriel.dscatalog.services.exceptions.DatabaseException;
import com.gabriel.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		return repository.findAll(pageRequest).map(product -> new ProductDTO(product));
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product product = obj.orElseThrow(() -> new EntityNotFoundException("Product not found"));
		return new ProductDTO(product, product.getCategories());
	}
	
	@Transactional
	public ProductDTO create(ProductDTO dto) {
		Product product = new Product();
		product = repository.save(product);
		return new ProductDTO(product);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product product = repository.getById(id);
			product = repository.save(product);
			return new ProductDTO(product);
		} catch (EntityNotFoundException  e) {
			throw new ResourceNotFoundException("Category not found " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} 
		
		catch (EmptyResultDataAccessException  e) {
			throw new ResourceNotFoundException("Category not found " + id);
		}
		
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
 
}
