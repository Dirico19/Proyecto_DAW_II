package com.cibertec.springboot.web.app.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cibertec.springboot.web.app.models.DAO.LibroRepository;
import com.cibertec.springboot.web.app.models.DAO.PrestamoRepository;
import com.cibertec.springboot.web.app.models.entity.CustomPage;
import com.cibertec.springboot.web.app.models.entity.Libro;
import com.cibertec.springboot.web.app.models.entity.Prestamo;
import com.cibertec.springboot.web.app.util.constants.Constant;

@Service
public class IPrestamoServiceImplementacion implements IPrestamoService {

	@Autowired
	private PrestamoRepository prestamoRepository;
	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private RestTemplate restTemplate;
	
	private final String BASIC_URL = Constant.API_URL + "/prestamos";

	@Override
	public Page<Prestamo> findAll(int page, int size) {
		String url = BASIC_URL + "?page=" + page + "&size=" + size;
		ParameterizedTypeReference<CustomPage<Prestamo>> responseType = new ParameterizedTypeReference<CustomPage<Prestamo>>() {};
		ResponseEntity<CustomPage<Prestamo>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
		CustomPage<Prestamo> customPage = response.getBody();
		List<Prestamo> listaPrestamos = customPage.getContent();
		System.out.println(customPage.getPageable().getPageNumber() + " - " + customPage.getPageable().getPageSize());
		Pageable pageable = PageRequest.of(customPage.getPageable().getPageNumber(), customPage.getPageable().getPageSize());
		int totalElements = customPage.getTotalElements();
		Page<Prestamo> prestamos = new PageImpl<>(listaPrestamos, pageable, totalElements);
		return prestamos;
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
