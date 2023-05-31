package com.cibertec.springboot.web.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "empleado")
public class Empleado implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_emp")
	private int id;
	
	@Column(name = "nom_emp")
	@NotEmpty(message = "Campo obligatorio")
	private String nombre;
	
	@Column(name = "ape_emp")
	@NotEmpty(message = "Campo obligatorio")
	private String apellido;
	
	@Column(name = "cel_emp")
	@NotEmpty(message = "Campo obligatorio")
	private String celular;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fec_contrato")
	@NotNull(message = "Campo obligatorio")
	private Date fecContrato;
	
	@Column(name = "cargo_emp")
	@NotEmpty(message = "Campo obligatorio")
	private String cargo;
	
	@Column(name = "suel_emp")
	@NotNull(message = "Campo obligatorio")
	private double sueldo;
	
	@Column(name = "cor_emp")
	@NotEmpty(message = "Campo obligatorio")
	private String correo;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usu")
	@NotNull(message = "Campo obligatorio")
	private Usuario usuario;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Date getFecContrato() {
		return fecContrato;
	}

	public void setFecContrato(Date fecContrato) {
		this.fecContrato = fecContrato;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public double getSueldo() {
		return sueldo;
	}

	public void setSueldo(double sueldo) {
		this.sueldo = sueldo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public String toString() {
		return "Empleado [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", celular=" + celular
				+ ", fecContrato=" + fecContrato + ", cargo=" + cargo + ", sueldo=" + sueldo + ", correo=" + correo
				+ ", usuario=" + usuario + "]";
	}

	private static final long serialVersionUID = 1L;
}
