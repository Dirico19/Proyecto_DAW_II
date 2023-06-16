package com.cibertec.springboot.web.app.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cibertec.springboot.web.app.models.DAO.PagoRepository;
import com.cibertec.springboot.web.app.models.DAO.PrestamoRepository;
import com.cibertec.springboot.web.app.models.entity.Pago;
import com.cibertec.springboot.web.app.models.entity.Prestamo;

@Service
public class IPagoServiceImplementacion implements IPagoService {

	@Autowired
	private PagoRepository pagoRepository;
	@Autowired
	private PrestamoRepository prestamoRepository;
	
	@Override
	public List<Pago> findAll() {
		return pagoRepository.findAll();
	}

	@Override
	public Page<Pago> findAll(Pageable pageable) {
		return pagoRepository.findAll(pageable);
	}

	@Override
	public void save(Pago pago) {
		pagoRepository.save(pago);
		Prestamo prestamo = pago.getPrestamo();
		prestamo.setMora("No");
		prestamoRepository.save(prestamo);
	}

	@Override
	public Pago findOne(int id) {
		return pagoRepository.findById(id).orElse(null);
	}
	
	@Override
	public Page<Pago> findByIdSocio(int idSocio, Pageable pageable) {
		return pagoRepository.findByIdSocio(idSocio, pageable);
	}

	@Override
	public Page<Pago> findByDate(Date fechaInicio, Date fechaFin, Pageable pageable) {
		return pagoRepository.findByDate(fechaInicio, fechaFin, pageable);
	}

	@Override
	public Page<Pago> findByDateAndSocio(Date fechaInicio, Date fechaFin, int idSocio, Pageable pageable) {
		return pagoRepository.findByDateAndSocio(fechaInicio, fechaFin, idSocio, pageable);
	}

}
