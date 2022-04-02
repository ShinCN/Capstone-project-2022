package com.gotoubun.weddingvendor.service;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IService<T> {
	Collection<T> findAll();

	Optional<T> findById(Long id);

	Optional<T> findByCategoryName(String name);

	T saveOrUpdate(T t);

	void deleteById(Long id);

}