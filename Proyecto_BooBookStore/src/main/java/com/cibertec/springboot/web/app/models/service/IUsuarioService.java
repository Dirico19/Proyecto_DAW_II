package com.cibertec.springboot.web.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.cibertec.springboot.web.app.models.entity.*;

public interface IUsuarioService {
	
	public List<Usuario> findAll();
	
	public Page<Usuario> findAll(Pageable pageable);
	
	public void save(Usuario usuario);
	
	public Usuario findOne(int Id);
	
	public Usuario findByNombre(String nombre);
}
