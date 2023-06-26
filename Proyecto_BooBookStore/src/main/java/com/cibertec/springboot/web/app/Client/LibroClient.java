package com.cibertec.springboot.web.app.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cibertec.springboot.web.app.models.entity.Libro;

@Component
public class LibroClient {

    @Autowired
    private RestTemplate restTemplate;

    public Libro getLibroById(Long id) {
        String url = "http://localhost:8080/api/libros/{id}";
        ResponseEntity<Libro> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Libro>() {}, id);
        return response.getBody();
    }

    public Libro[] getLibros() {
        String url = "http://localhost:8080/api/libros";
        ResponseEntity<Libro[]> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<Libro[]>() {});
        return response.getBody();
    }



}
