package com.cibertec.springboot.web.app.models.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.cibertec.springboot.web.app.models.entity.Libro;

public interface ILibroService {

	public Page<Libro> findAll(int page, int size);
	
	public ResponseEntity<String> save(Libro libro);
	
	public ResponseEntity<String> update(Libro libro);
	
	public ResponseEntity<String> findOne(Long id);
	
	public ResponseEntity<String> delete(Long id);
	
}
