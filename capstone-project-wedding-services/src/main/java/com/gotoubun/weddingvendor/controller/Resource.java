package com.gotoubun.weddingvendor.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface Resource<T> {
	//search text
	@GetMapping("/search/{searchText}")
	ResponseEntity<Page<T>> findAll(Pageable pageable, @PathVariable String searchText);

	//find all items
	@GetMapping
	ResponseEntity<Page<T>> findAll(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//get by Id
	@GetMapping("{id}")
	ResponseEntity<?> findById(@PathVariable Long id);
	
	//add T
	@PostMapping
	ResponseEntity<?> save(@RequestBody T t);
	
	//update T
	@PutMapping
	ResponseEntity<?> update(@RequestBody T t);
	
	//delete by id
	@DeleteMapping("{id}")
	ResponseEntity<?> deleteById(@PathVariable Long id);
}
