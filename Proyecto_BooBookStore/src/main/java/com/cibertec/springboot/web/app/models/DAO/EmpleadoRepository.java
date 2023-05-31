package com.cibertec.springboot.web.app.models.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cibertec.springboot.web.app.models.entity.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

	@Query("select e from Empleado e where e.usuario.nombre = :usuario")
	public Empleado findByUsuario(@Param("usuario") String usuario);
	
}
