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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "socio")
public class Socio implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_soc")
	private int id;
	
	@Column(name = "nom_soc")
	@NotEmpty(message = "Campo obligatorio")
	private String nombre;
	
	@Column(name = "ape_soc")
	@NotEmpty(message = "Campo obligatorio")
	private String apellido;
	
	@Column(name = "dni_soc")
	@NotEmpty(message = "Campo obligatorio")
	private String dni;
	
	@Column(name = "fec_nac")
	@NotNull(message = "Campo obligatorio")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecNacimiento;
	
	@Column(name = "fec_reg")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecRegistro;
	
	@Column(name = "cel_soc")
	@NotEmpty(message = "Campo obligatorio")
	private String celular;
	
	@Column(name = "cor_soc")
	@NotEmpty(message = "Campo obligatorio")
	@Email(message = "Debe ser un correo electrónico")
	private String correo;
	
	@Column(name = "deuda")
	private double deuda;
	
	@OneToOne
	@JoinColumn(name = "id_usu")
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

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Date getFecNacimiento() {
		return fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}

	public Date getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public double getDeuda() {
		return deuda;
	}

	public void setDeuda(double deuda) {
		this.deuda = deuda;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	private static final long serialVersionUID = 1L;

}
