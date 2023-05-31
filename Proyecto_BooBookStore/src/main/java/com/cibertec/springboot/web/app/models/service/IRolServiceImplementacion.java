package com.cibertec.springboot.web.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.springboot.web.app.models.DAO.RolRepository;
import com.cibertec.springboot.web.app.models.entity.Rol;

@Service
public class IRolServiceImplementacion implements IRolService {

	@Autowired
	private RolRepository rolRepository;
	
	@Override
	public List<Rol> findAll() {
		return rolRepository.findAll();
	}

	@Override
	public Rol findOne(int id) {
		return rolRepository.findById(id).orElse(null);
	}

}
