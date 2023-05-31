package com.cibertec.springboot.web.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.springboot.web.app.models.DAO.CategoriaRepository;
import com.cibertec.springboot.web.app.models.entity.Categoria;

@Service
public class ICategoriaServiceImplementacion implements ICategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepo;
	
	@Override
	@Transactional(readOnly = true)
	public List<Categoria> findAll() {
		return categoriaRepo.findAll();
	}

}
