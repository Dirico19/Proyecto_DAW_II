package com.cibertec.springboot.web.app.models.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cibertec.springboot.web.app.models.entity.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

}