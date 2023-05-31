package com.cibertec.springboot.web.app.models.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cibertec.springboot.web.app.models.entity.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

	@Query(value = "select pa.id_pago, pa.id_pres, pa.fec_pago, pa.monto from pago as pa inner join prestamo as pr on pr.id_pres=pa.id_pres inner join socio as s on pr.id_soc=s.id_soc where s.id_soc = ?1", nativeQuery = true)
	  List<Pago> findByIdSocio(int idSocio);
	
	@Query(value = "select pa.id_pago, pa.id_pres, pa.fec_pago, pa.monto from pago as pa inner join prestamo as pr on pr.id_pres=pa.id_pres inner join socio as s on pr.id_soc=s.id_soc where pa.fec_pago  = ?1", nativeQuery = true)
	  List<Pago> findByDate(String fecha);
	
	@Query(value = "select pa.id_pago, pa.id_pres, pa.fec_pago, pa.monto from pago as pa inner join prestamo as pr on pr.id_pres=pa.id_pres inner join socio as s on pr.id_soc=s.id_soc where pa.fec_pago  = ?1 and s.id_soc= ?2", nativeQuery = true)
	  List<Pago> findByDateAndSocio(String fecha, int idSocio);
	
}
