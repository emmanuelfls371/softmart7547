package edu.tdp2.client.dto;

import java.io.Serializable;

import com.google.gwt.validation.client.Max;
import com.google.gwt.validation.client.Min;
import com.google.gwt.validation.client.NotEmpty;
import com.google.gwt.validation.client.NotNull;
import com.google.gwt.validation.client.interfaces.IValidatable;

public class ProyectoDto implements IValidatable, Serializable,Dto{

	private static final long serialVersionUID = 111116217892037317L;
	
	@NotEmpty(message = "Debe ingresar el nombre")
	@NotNull (message = "Debe ingresar el nombre")
	private String nombre;

	@NotEmpty(message = "Debe ingresar el rango de presupuesto")
	@NotNull (message = "Debe ingresar el rango de presupuesto")
	private String presupuesto;

	
	@Max(maximum=32, message = "Supera maximo de dias")
	@Min(minimum=0, message = "Por debajo de minimo de dias")
	private int dia;
	
	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	
	@Max(maximum=13, message = "Supera maximo de meses")
	@Min(minimum=0, message = "Por debajo de minimo de meses")
	private int mes;
	
	
	@Max(maximum=2021, message = "Supera maximo de años")
	@Min(minimum=2008, message = "Por debajo de minimo de años")
	private int anio;

	@NotEmpty(message = "Debe ingresar el nivel de reputación")
	@NotNull (message = "Debe ingresar el nivel de reputación")
	private String nivel;
	
	@NotEmpty(message = "Debe ingresar la dificultad")
	@NotNull (message = "Debe ingresar la dificultad")
	private String dificultad;
	
	@NotEmpty (message = "Debe ingresar el tamaño")
	@NotNull (message = "Debe ingresar el tamaño")
	private String tamanio;
	
	private String descripcion;

	private String archivo;
	
	private String usuario;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(String presupuesto) {
		this.presupuesto = presupuesto;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public String getTamanio() {
		return tamanio;
	}

	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	
}
