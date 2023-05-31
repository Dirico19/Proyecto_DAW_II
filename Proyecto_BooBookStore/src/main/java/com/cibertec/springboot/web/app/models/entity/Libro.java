package com.cibertec.springboot.web.app.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="libro")
public class Libro implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_lib")
	private Long id;
	@NotEmpty(message = "Campo obligatorio")
	@Column(name="titulo_lib")
	private String titulo;
	@NotEmpty(message = "Campo obligatorio")
	@Column(name="autor_lib")
	private String autor;	
	@NotNull(message = "Campo obligatorio")
	@Min(value = 0, message = "Debe ser mayor a 0")
	@Column(name="año_lib")
	private int anio;
	@NotNull(message = "Campo obligatorio")
	@ManyToOne
	@JoinColumn(name="id_cat")
	private Categoria categoria;
	@NotNull(message = "Campo obligatorio")
	@Min(value = 0, message = "El valor mínimo es 0")
	@Column(name="stk_lib")
	private int stock;
	private String foto;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}

	private static final long serialVersionUID = 1L;
}
