package com.cibertec.springboot.web.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cibertec.springboot.web.app.models.entity.Pago;

public interface IPagoService {

	public List<Pago> findAll();
	
	public Page<Pago> findAll(Pageable pageable);
	
	public void save(Pago pago);
	
	public Pago findOne(int id);
	
	public List<Pago> findByIdSocio(int idSocio);
	
	public List<Pago> findByDate(String fecha);
	
	public List<Pago> findByDateAndSocio(String fecha, int idSocio);
	
}
