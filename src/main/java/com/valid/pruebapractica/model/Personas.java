package com.valid.pruebapractica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "personas")
public class Personas {

	private long id;
	private String nombre;
	private String apellido;
	private String procesado;
	
	public Personas() {
		
	}
	
	public Personas(String nombre, String apellido, String procesado) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.procesado = procesado;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "nombre", nullable = false)
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Column(name = "apellido", nullable = false)
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	@Column(name = "procesado", nullable = false)
	public String getProcesado() {
		return procesado;
	}
	public void setProcesado(String procesado) {
		this.procesado = procesado;
	}

	@Override
	public String toString() {
		return "Personas [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", procesado=" + procesado
				+ "]";
	}
	
}
