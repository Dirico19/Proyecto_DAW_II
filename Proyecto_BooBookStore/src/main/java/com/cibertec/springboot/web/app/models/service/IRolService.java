package com.cibertec.springboot.web.app.models.service;

import java.util.List;

import com.cibertec.springboot.web.app.models.entity.Rol;

public interface IRolService {

	public List<Rol> findAll();
	
	public Rol findOne(int id);
}
