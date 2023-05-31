package com.cibertec.springboot.web.app.models.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.springboot.web.app.models.entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

	@Query("select count(1) from Prestamo p where p.socio.id=:idSocio and p.estado='Pendiente'")
	public int devolucionesPendientes(int idSocio);
	
	@Query("select count(1) from Prestamo p where p.socio.id=:idSocio and p.mora='Si'")
	public int morasPendientes(int idSocio);
	
	@Query(value = "SELECT * FROM Prestamo WHERE fec_pres = ?1 and id_soc = ?2", nativeQuery = true)
	  List<Prestamo> findByDateAndSocio(String date, int idSocio);
	
	@Query(value = "SELECT * FROM Prestamo WHERE id_soc = ?1 and fec_pres", nativeQuery = true)
	  List<Prestamo> findByIdSocio(int idSocio);
	
	@Query(value = "SELECT * FROM Prestamo WHERE fec_pres = ?1", nativeQuery = true)
	  List<Prestamo> findByDate(String date);
	
}
