package com.cibertec.springboot.web.app.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cibertec.springboot.web.app.models.DAO.LibroRepository;
import com.cibertec.springboot.web.app.models.DAO.PrestamoRepository;
import com.cibertec.springboot.web.app.models.entity.Libro;
import com.cibertec.springboot.web.app.models.entity.Prestamo;

@Service
public class IPrestamoServiceImplementacion implements IPrestamoService {

	@Autowired
	private PrestamoRepository prestamoRepository;
	@Autowired
	private LibroRepository libroRepository;
	
	@Override
	public List<Prestamo> findAll() {
		return prestamoRepository.findAll();
	}

	@Override
	public Page<Prestamo> findAll(Pageable pageable) {
		return prestamoRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Prestamo prestamo) {
		if (prestamo.getId() == 0) {
			Libro libro = prestamo.getLibro();
			libro.setStock(libro.getStock()-1);
			libroRepository.save(libro);			
		} else {
			Libro libro = prestamo.getLibro();
			libro.setStock(libro.getStock()+1);
			libroRepository.save(libro);
		}
		prestamoRepository.save(prestamo);
	}

	@Override
	public Prestamo findOne(int id) {
		return prestamoRepository.findById(id).orElse(null);
	}

	@Override
	public int devolucionesPendientes(int id) {
		return prestamoRepository.devolucionesPendientes(id);
	}

	@Override
	public int morasPendientes(int id) {
		return prestamoRepository.morasPendientes(id);
	}
	
	@Override
	public List<Prestamo> findByIdSocio(int idSocio) {
		return prestamoRepository.findByIdSocio(idSocio);
	}

	@Override
	public List<Prestamo> findByDateAndSocio(String date, int idSocio) {
		return prestamoRepository.findByDateAndSocio(date, idSocio);

	}

	@Override
	public List<Prestamo> findByDate(String date) {
		return prestamoRepository.findByDate(date);
	}

}
