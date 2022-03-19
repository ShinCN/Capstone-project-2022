package com.gotoubun.weddingvendor.service;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IService<T> {
    Collection<T> findAll();
	
	Optional<T> findById(Long id);
	
	T saveOrUpdate(T t);

	void deleteById(Long id);

	List<T> saveAll(List<T> t);
}
