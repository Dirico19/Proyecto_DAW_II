package com.cibertec.springboot.web.app.models.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.cibertec.springboot.web.app.models.entity.*;

public interface ILibroService {

	public List<Libro> findAll();	
	
	public Page<Libro> findAll(Pageable pageable);
	
	public void save(Libro libro);
	
	public Libro findOne(Long id);
	
	public void delete(Long id);
	
	public void prestarLibro(Libro libro);
}
