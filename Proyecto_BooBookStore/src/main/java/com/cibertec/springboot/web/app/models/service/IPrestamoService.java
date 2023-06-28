package com.cibertec.springboot.web.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cibertec.springboot.web.app.models.entity.Prestamo;

public interface IPrestamoService {
	
	public Page<Prestamo> findAll(int page, int size);
	
	public void save(Prestamo prestamo);
	
	public Prestamo findOne(int id);
	
	public int devolucionesPendientes(int id);
	
	public int morasPendientes(int id);
	
	public List<Prestamo> findByIdSocio(int idSocio);
	
	public List<Prestamo> findByDateAndSocio(String date, int idSocio);
	
	public List<Prestamo> findByDate(String date);
	
}
