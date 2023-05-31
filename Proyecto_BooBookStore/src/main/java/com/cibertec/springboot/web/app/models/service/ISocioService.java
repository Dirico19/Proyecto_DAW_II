package com.cibertec.springboot.web.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cibertec.springboot.web.app.models.entity.Socio;

public interface ISocioService {

	public List<Socio> findAll();
	
	public Page<Socio> findAll(Pageable pageable);
	
	public void save(Socio socio);
	
	public Socio findOne(int id);
	
	public void delete(int id);
	
	public Socio findbyUsuario(String usuario);
	
}
