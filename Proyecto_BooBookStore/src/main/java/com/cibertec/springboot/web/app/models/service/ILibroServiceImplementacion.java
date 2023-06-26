package com.cibertec.springboot.web.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cibertec.springboot.web.app.models.entity.CustomPage;
import com.cibertec.springboot.web.app.models.entity.Libro;
import com.cibertec.springboot.web.app.util.constants.Constant;


@Service
public class ILibroServiceImplementacion implements ILibroService {

	@Autowired
	private RestTemplate restTemplate;
	
	private final String BASIC_URL = Constant.API_URL + "/libros";

	@Override
	public Page<Libro> findAll(int page, int size) {
		String url = BASIC_URL + "?page=" + page + "&size=" + size;
		ParameterizedTypeReference<CustomPage<Libro>> responseType = new ParameterizedTypeReference<CustomPage<Libro>>() {};
		ResponseEntity<CustomPage<Libro>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
		CustomPage<Libro> customPage = response.getBody();
		List<Libro> listaLibros = customPage.getContent();
		System.out.println(customPage.getPageable().getPageNumber() + " - " + customPage.getPageable().getPageSize());
		Pageable pageable = PageRequest.of(customPage.getPageable().getPageNumber(), customPage.getPageable().getPageSize());
		int totalElements = customPage.getTotalElements();
		Page<Libro> libros = new PageImpl<>(listaLibros, pageable, totalElements);
		return libros;
	}

	@Override
	public ResponseEntity<String> save(Libro libro) {
		String url = BASIC_URL + "/registrar";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Libro> requestEntity = new HttpEntity<>(libro, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		return response;
	}

	@Override
	public ResponseEntity<String> update(Libro libro) {
		String url = BASIC_URL + "/actualizar";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Libro> requestEntity = new HttpEntity<>(libro, headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
		return response;
	}
	
	@Override
	public ResponseEntity<String> findOne(Long id) {
		String url = BASIC_URL + "/" + id;
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		return response;
	}

	@Override
	public ResponseEntity<String> delete(Long id) {
		String url = BASIC_URL + "/eliminar/" + id;
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
		return response;
	}
	
}
