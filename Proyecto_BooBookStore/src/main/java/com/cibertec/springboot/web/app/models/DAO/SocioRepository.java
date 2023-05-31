package com.cibertec.springboot.web.app.models.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec.springboot.web.app.models.entity.Socio;

public interface SocioRepository extends JpaRepository<Socio, Integer> {

	@Query("select s from Socio s where s.usuario.nombre = :usuario")
	public Socio findByUsuario(@Param("usuario") String usuario);
	
}
