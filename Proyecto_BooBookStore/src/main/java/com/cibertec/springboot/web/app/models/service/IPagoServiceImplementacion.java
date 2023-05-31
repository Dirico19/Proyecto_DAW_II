package com.cibertec.springboot.web.app.models.service;

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
	public List<Pago> findByIdSocio(int idSocio) {
		return pagoRepository.findByIdSocio(idSocio);
	}

	@Override
	public List<Pago> findByDate(String fecha) {
		return pagoRepository.findByDate(fecha);
	}

	@Override
	public List<Pago> findByDateAndSocio(String fecha, int idSocio) {
		return pagoRepository.findByDateAndSocio(fecha, idSocio);
	}

}
