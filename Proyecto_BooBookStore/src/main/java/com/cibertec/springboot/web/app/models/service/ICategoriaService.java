package com.cibertec.springboot.web.app.models.service;

import java.util.List;
import com.cibertec.springboot.web.app.models.entity.Categoria;

public interface ICategoriaService {

	public List<Categoria> findAll();
}
