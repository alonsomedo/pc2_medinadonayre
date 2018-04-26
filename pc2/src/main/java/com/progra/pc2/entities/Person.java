package com.progra.pc2.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Person {
	
	@GeneratedValue
	@Id
	private Integer id;
	
	@NotNull
	@Size(min=2, max=30, message="Ingrese nuevamente su nombre")
	private String name; 
	
	@NotNull
	@Size(min=8, max=8, message="El dni es de 8 dígitos")
	private String dni;
	
	@NotNull
	private double salario;
	
	@NotNull
	private String tipoTrabajador;
	
	@NotNull
	private double uit;
	
	private double totalImpuesto;
	
	private double totalBruto;
	
	
	public String getTipoTrabajador() {
		return tipoTrabajador;
	}
	public void setTipoTrabajador(String tipoTrabajador) {
		this.tipoTrabajador = tipoTrabajador;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	public double getTotalImpuesto() {
		return totalImpuesto;
	}
	public void setTotalImpuesto(double totalImpuesto) {
		this.totalImpuesto = totalImpuesto;
	}
	public double getTotalBruto() {
		return totalBruto;
	}
	public void setTotalBruto(double totalBruto) {
		this.totalBruto = totalBruto;
	}
	public double getUit() {
		return uit;
	}
	public void setUit(double uit) {
		this.uit = uit;
	}

	
	

}
