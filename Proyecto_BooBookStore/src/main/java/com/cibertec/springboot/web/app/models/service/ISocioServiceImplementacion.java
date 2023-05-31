package com.cibertec.springboot.web.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cibertec.springboot.web.app.models.DAO.SocioRepository;
import com.cibertec.springboot.web.app.models.entity.Socio;

@Service
public class ISocioServiceImplementacion implements ISocioService {

	@Autowired
	private SocioRepository socioRepository;
	
	@Override
	public List<Socio> findAll() {
		return socioRepository.findAll();
	}

	@Override
	public Page<Socio> findAll(Pageable pageable) {
		return socioRepository.findAll(pageable);
	}

	@Override
	public void save(Socio socio) {
		socioRepository.save(socio);
	}

	@Override
	public Socio findOne(int id) {
		return socioRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(int id) {
		socioRepository.deleteById(id);
	}

	@Override
	public Socio findbyUsuario(String usuario) {
		return socioRepository.findByUsuario(usuario);
	}

}
