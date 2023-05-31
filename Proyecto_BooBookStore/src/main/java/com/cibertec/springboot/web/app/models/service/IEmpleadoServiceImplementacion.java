package com.cibertec.springboot.web.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cibertec.springboot.web.app.models.DAO.EmpleadoRepository;
import com.cibertec.springboot.web.app.models.entity.Empleado;

@Service
public class IEmpleadoServiceImplementacion implements IEmpleadoService {

	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Override
	public List<Empleado> findAll() {
		return empleadoRepository.findAll();
	}

	@Override
	public Page<Empleado> findAll(Pageable pageable) {
		return empleadoRepository.findAll(pageable);
	}

	@Override
	public void save(Empleado empleado) {
		empleadoRepository.save(empleado);
	}

	@Override
	public Empleado findOne(int id) {
		return empleadoRepository.findById(id).orElse(null);
	}

	@Override
	public void delete(int id) {
		empleadoRepository.deleteById(id);
	}

	@Override
	public Empleado findByUsuario(String usuario) {
		return empleadoRepository.findByUsuario(usuario);
	}

}
