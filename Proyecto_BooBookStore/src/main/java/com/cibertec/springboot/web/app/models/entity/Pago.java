package com.cibertec.springboot.web.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pago")
public class Pago implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pago")
	private int id;
	@OneToOne
	@JoinColumn(name = "id_pres")
	@NotNull(message = "Campo obligatorio")
	private Prestamo prestamo;
	@Column(name = "fec_pago")
	private Date fecPago;
	@Column(name = "monto")
	private double monto;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public Date getFecPago() {
		return fecPago;
	}

	public void setFecPago(Date fecPago) {
		this.fecPago = fecPago;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	private static final long serialVersionUID = 1L;

	
	
}
